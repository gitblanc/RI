/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

/**
 * @author UO285176
 *
 */
public class DeleteLastPayrollForMechanic implements Command<PayrollBLDto> {

	String mechanicDni = null;

	public DeleteLastPayrollForMechanic(String mechanicId) {
		Argument.isNotNull(mechanicId, "The mechanic id can't be null");
		Argument.isNotEmpty(mechanicId, "The mechanic id can't be empty");
		this.mechanicDni = mechanicId;
	}

	@Override
	public PayrollBLDto execute() throws BusinessException {
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		MechanicGateway mg = PersistenceFactory.forMechanic();
		ContractGateway cg = PersistenceFactory.forContract();
		// Comprobamos que el mecánico exista
		Optional<MechanicDALDto> mechanic = mg.findByDni(mechanicDni);
		BusinessCheck.isTrue(!mechanic.isEmpty(), "The mechanic must exist");
		// Miramos que el mecánico tenga un contrato en vigor
		Optional<ContractDALDto> contract = cg
				.findContractInForceById(mechanic.get().id);
//		BusinessCheck.isTrue(!contract.isEmpty(),
//				"The mechanic must have a contract");
		// Encontramos las nóminas que tiene ese contrato asociado
		List<PayrollDALDto> payrolls = pg.findByContractId(contract.get().id);
		PayrollDALDto payroll = findLastPayroll(payrolls);
		// Eliminamos la payroll
		pg.remove(payroll.id);
		return null;
	}

	/*
	 * Método que busca la última payroll
	 */
	private PayrollDALDto findLastPayroll(List<PayrollDALDto> payrolls) {
		PayrollDALDto payroll = payrolls.get(0);
		for (int i = 1; i < payrolls.size(); i++) {
			if (payrolls.get(i).date.getMonthValue() > payroll.date
					.getMonthValue())
				payroll = payrolls.get(i);
		}
		return payroll;
	}

}
