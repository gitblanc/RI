/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.Payroll;

/**
 * @author UO285176
 *
 */
public class GeneratePayrolls implements Command<Void> {

    private LocalDate date;

    private ContractRepository crepo = Factory.repository.forContract();
    private MechanicRepository mrepo = Factory.repository.forMechanic();
    private PayrollRepository repo = Factory.repository.forPayroll();

    public GeneratePayrolls() {
	this(null);
    }

    public GeneratePayrolls(LocalDate present) {
	if (present == null)
	    this.date = LocalDate.now();
	else
	    this.date = present;
    }

    @Override
    public Void execute() throws BusinessException {
	// Listamos todos los contratos
	List<Contract> contracts = crepo.findAll();
	// Si hay contratos
	if (!contracts.isEmpty()) {
	    // Listamos las payrolls
	    for (Contract c : contracts) {
		if (c.getMechanic().isPresent()) {
		    if (c.getState().equals(ContractState.IN_FORCE)) {
			List<Payroll> payrolls = repo.findByContract(c.getId());
			Optional<Payroll> payroll = findPayroll(payrolls);
			// Si el contrato no tiene nóminas asignadas
			if (payroll == null) {
			    generateNewPayroll(c);
			}
		    }
		} else if (c.getState().equals(ContractState.TERMINATED)) {
		    if (c.getEndDate().isPresent()) {
			if (c.getEndDate().get().getMonthValue() == this.date
				.getMonthValue()
				&& c.getEndDate().get().getYear() == this.date
					.getYear()) {
			    Optional<Payroll> p = repo.findByDate(this.date);
			    if (p.isEmpty()) {
				generateNewPayroll(c);
			    }
			}
		    }
		}
	    }
	}
	return null;
    }

    private Optional<Payroll> findPayroll(List<Payroll> payrolls) {
	Optional<Payroll> payroll = null;
	for (Payroll p : payrolls) {
	    if (p.getDate().getMonthValue() == this.date.getMonthValue()
		    && p.getDate().getYear() == this.date.getYear())
		payroll = Optional.ofNullable(p);
	}
	return payroll;
    }

    private void generateNewPayroll(Contract c) {
	Payroll p = new Payroll(c, date);
	repo.add(p);
    }

}

class OrdenarContratosTerminados implements Comparator<Contract> {

    @Override
    public int compare(Contract o1, Contract o2) {
	return o1.getEndDate().get().compareTo(o2.getEndDate().get());
    }
}