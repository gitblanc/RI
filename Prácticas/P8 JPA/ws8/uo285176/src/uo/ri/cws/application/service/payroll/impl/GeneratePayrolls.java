/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.time.LocalDate;
import java.util.ArrayList;
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
import uo.ri.cws.domain.Mechanic;
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
		    Optional<Payroll> p = repo
			    .findCurrentMonthByContractId(c.getId());
		    if (p.isPresent()) {
			generateNewPayroll(c);
		    }
		}
	    }
	}
//	if (p != null) {
//	    List<Mechanic> mechanics = mrepo.findAll();
//	    if (!mechanics.isEmpty()) {
//		for (Mechanic m : mechanics) {
//		    List<Contract> terminated = new ArrayList<>(
//			    m.getTerminatedContracts());
//		    if (!terminated.isEmpty()) {
//			for (Contract c : terminated) {
//			    if (c.getEndDate().get().getMonthValue() == p
//				    .getDate().getMonthValue()
//				    && c.getEndDate().get().getYear() == p
//					    .getDate().getYear())
//				generateNewPayroll(c);
//			}
//		    }
//
//		}
//	    }
//
//	}
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

//	List<Contract> contracts = crepo.findAll();
//	if (!contracts.isEmpty()) {
//	    for (Contract c : contracts) {
//		if (c.getState().equals(ContractState.IN_FORCE)
//			|| c.getState().equals(ContractState.TERMINATED)) {
//		    if (c.getMechanic().isPresent()) {
//
//			Optional<Payroll> lastPayroll = repo
//				.findCurrentMonthByContractId(c.getId());
//			if (lastPayroll.isEmpty()) {
//			    generateNewPayroll(c);
//			}
//		    }
//		}
//	    }
//	}

    private void generateTerminatedPayrolls() {
	List<Mechanic> mechanics = mrepo.findAll();
	if (!mechanics.isEmpty()) {
	    for (Mechanic m : mechanics) {
		List<Contract> terminatedContracts = new ArrayList<>(
			m.getTerminatedContracts());
		if (!terminatedContracts.isEmpty()) {
		    for (Contract c : terminatedContracts) {
			Optional<Payroll> lastPayroll = repo
				.findCurrentMonthByContractId(c.getId());
			if (lastPayroll.isEmpty())
			    generateNewPayroll(c);
		    }
		}
	    }
	}
    }

    private void generateInForcePayrolls() {
	List<Mechanic> mechanics = mrepo.findAllInForce();
	if (!mechanics.isEmpty()) {
	    for (Mechanic m : mechanics) {
		List<Contract> contracts = new ArrayList<>(
			m.getTerminatedContracts());
		for (Contract c : contracts) {

		    Optional<Payroll> lastPayroll = repo
			    .findCurrentMonthByContractId(c.getId());
		    if (lastPayroll.isEmpty())
			generateNewPayroll(c);
		}

		Optional<Contract> c = m.getContractInForce();

		if (c.isPresent()) {
		    Optional<Payroll> lastPayroll = repo
			    .findCurrentMonthByContractId(c.get().getId());
		    if (lastPayroll.isEmpty())
			generateNewPayroll(c.get());
		}

	    }
	}
    }

    private Contract findLastTerminatedContract(Payroll p,
	    List<Contract> terminatedContracts) {
	terminatedContracts.sort(new OrdenarContratosTerminados());
	int month = p.getDate().getMonthValue();
	int year = p.getDate().getYear();
	Contract c = null;
	for (Contract co : terminatedContracts) {
	    if (co.getEndDate().get().getMonthValue() == month
		    && co.getEndDate().get().getYear() == year)
		c = co;
	}
	return c;
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