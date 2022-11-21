/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class GetAllPayrollsForMechanic
	implements Command<List<PayrollSummaryBLDto>> {

    private String id = null;
    private MechanicRepository mrepo = Factory.repository.forMechanic();

    public GetAllPayrollsForMechanic(String id) {
	ArgumentChecks.isNotNull(id, "The mechanic id can't be null");
	ArgumentChecks.isNotEmpty(id, "The mechanic id can't be empty");
	ArgumentChecks.isNotBlank(id, "The id can't be blank");
	this.id = id;
    }

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
	Optional<Mechanic> m = mrepo.findById(id);
	BusinessChecks.isTrue(m.isPresent(), "mechanic must exist");
	List<Payroll> payrolls = new ArrayList<>(
		m.get().getContractInForce().get().getPayrolls());
	return DtoAssembler.toPayrollSummaryDto(payrolls);
    }

}
