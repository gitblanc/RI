/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.Payroll;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class DeleteLastPayrollsFor implements Command<Void> {

    private String mechanicId = null;
    private PayrollRepository repo = Factory.repository.forPayroll();
    private MechanicRepository mrepo = Factory.repository.forMechanic();

    public DeleteLastPayrollsFor(String id) {
	ArgumentChecks.isNotNull(id, "The mechanic id can't be null");
	ArgumentChecks.isNotEmpty(id, "The mechanic id can't be empty");
	ArgumentChecks.isNotBlank(id, "The id can't be blank");
	this.mechanicId = id;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<Mechanic> mechanic = mrepo.findById(mechanicId);
	BusinessChecks.isTrue(mechanic.isPresent(), "mechanic must exist");
	List<Payroll> payrolls = new ArrayList<>(
		mechanic.get().getContractInForce().get().getPayrolls());
	if (!payrolls.isEmpty())
	    deleteLastPayrolls(payrolls);
	return null;
    }

    private void deleteLastPayrolls(List<Payroll> payrolls) {
	Payroll last = findLastPayroll(payrolls);
	for (Payroll p : payrolls) {
	    if (p.getDate().getMonthValue() == last.getDate().getMonthValue()
		    && p.getDate().getYear() == last.getDate().getYear()) {
		repo.remove(p);
	    }
	}
    }

    private Payroll findLastPayroll(List<Payroll> payrolls) {
	Payroll payroll = payrolls.get(0);
	for (int i = 1; i < payrolls.size(); i++) {
	    Payroll p = payrolls.get(i);
	    if (p.getDate().compareTo(payroll.getDate()) > 0)
		payroll = p;
	}
	return payroll;
    }

}
