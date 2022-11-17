/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class GetAllPayrollsForProfessionalGroup
	implements Command<List<PayrollSummaryBLDto>> {

    private String name = null;
    private ProfessionalGroupRepository pgrepo = Factory.repository
	    .forProfessionalGroup();

    public GetAllPayrollsForProfessionalGroup(String name) {
	ArgumentChecks.isNotNull(name, "The group id can't be null");
	ArgumentChecks.isNotEmpty(name, "The group id can't be empty");
	ArgumentChecks.isNotBlank(name, "The group can't be blank");
	this.name = name;
    }

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
	Optional<ProfessionalGroup> group = pgrepo.findByName(name);
	BusinessChecks.isTrue(group.isPresent(), "group must exist");
	List<Contract> contracts = new ArrayList<>(group.get().getContracts());
	List<Payroll> payrolls = new ArrayList<>();
	for (Contract c : contracts) {
	    List<Payroll> pC = new ArrayList<>(c.getPayrolls());
	    for (Payroll p : pC) {
		payrolls.add(p);
	    }
	}
	return DtoAssembler.toPayrollSummaryDto(payrolls);
    }

}
