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
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

/**
 * @author UO285176
 *
 */
public class GetAllPayrollsForProfessionalGroup
		implements Command<List<PayrollSummaryBLDto>> {

	String groupName = null;

	public GetAllPayrollsForProfessionalGroup(String name) {
		Argument.isNotNull(name, "The name can't be null");
		Argument.isNotEmpty(name, "The name can't be empty");
		this.groupName = name;
	}

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		ProfessionalGroupGateway pgroup = PersistenceFactory
				.forProfessionalGroup();
		// Obtenemos el grupo
		Optional<ProfessionalGroupDALDto> group = pgroup.findByName(groupName);
		BusinessCheck.isTrue(!group.isEmpty(), "The group must exist");
		ContractGateway cg = PersistenceFactory.forContract();
		// Buscamos el contrato
		List<ContractDALDto> contracts = cg
				.findContractsByProfessionalGroup(group.get().id);
		// Buscamos las payrolls
		List<PayrollSummaryBLDto> payrolls = findPayrolls(contracts);
		return payrolls;
	}

	private List<PayrollSummaryBLDto> findPayrolls(
			List<ContractDALDto> contracts) {
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		List<PayrollSummaryBLDto> payrolls = new ArrayList<>();
		for (ContractDALDto c : contracts) {
			List<PayrollDALDto> payrollsFound = pg.findByContractId(c.id);
			for (PayrollDALDto p : payrollsFound) {
				payrolls.add(PayrollAssembler.toSummaryBLDto(p));
			}
		}
		return payrolls;
	}

}
