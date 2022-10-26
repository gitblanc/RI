/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractSummaryDALDto;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

/**
 * @author UO285176
 *
 */
public class GetAllPayrollsForMechanic
		implements Command<List<PayrollSummaryBLDto>> {

	String mechanicId = null;

	public GetAllPayrollsForMechanic(String id) {
		Argument.isNotNull(id, "The id can't be null");
		Argument.isNotEmpty(id, "The id can't be empty");
		this.mechanicId = id;
	}

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		MechanicGateway mg = PersistenceFactory.forMechanic();
		// Comprobamos que el mecánico exista
		Optional<MechanicDALDto> mechanic = mg.findById(mechanicId);
		BusinessCheck.isTrue(!mechanic.isEmpty(), "The mechanic must exist");
		// Buscamos los contractos del mecánico
		ContractGateway cg = PersistenceFactory.forContract();
		List<ContractSummaryDALDto> contracts = cg
				.findContractsByDni(mechanic.get().dni);
		// Buscamos las payrolls asociadas a los contratos
		List<PayrollSummaryBLDto> payrolls = findPayrolls(contracts);
		return payrolls;
	}

	private List<PayrollSummaryBLDto> findPayrolls(
			List<ContractSummaryDALDto> contracts) {
		List<PayrollSummaryBLDto> payrolls = new ArrayList<>();
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		for (ContractSummaryDALDto c : contracts) {
			List<PayrollDALDto> payrollsFound = pg.findByContractId(c.id);
			// Si encuentra payrolls asociadas al contrato
			if (!payrollsFound.isEmpty() || payrollsFound != null) {
				for (PayrollDALDto p : payrollsFound) {
					payrolls.add(PayrollAssembler.toSummaryBLDto(p));
				}
			}
		}
		return payrolls;
	}

}
