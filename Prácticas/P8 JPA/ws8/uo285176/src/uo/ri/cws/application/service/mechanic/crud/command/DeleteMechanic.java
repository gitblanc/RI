package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.domain.Contract.ContractState;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;

    private MechanicRepository repo = Factory.repository.forMechanic();
    private WorkOrderRepository wrepo = Factory.repository.forWorkOrder();
    private ContractRepository crepo = Factory.repository.forContract();

    public DeleteMechanic(String mechanicId) {
	ArgumentChecks.isNotNull(mechanicId, "The id can't be null");
	ArgumentChecks.isNotEmpty(mechanicId, "The id can't be empty");
	ArgumentChecks.isNotBlank(mechanicId, "The id can't be black string");
	this.mechanicId = mechanicId;
    }

    public Void execute() throws BusinessException {
	Optional<Mechanic> m = repo.findById(mechanicId);
	List<WorkOrder> workOrders = wrepo.findByMechanic(mechanicId);
	List<Contract> contracts = crepo.findByMechanicId(mechanicId);
	List<Contract> terminatedContracts = obtainContractsByState(contracts,
		ContractState.TERMINATED);
	List<Contract> inForceContracts = obtainContractsByState(contracts,
		ContractState.IN_FORCE);
	BusinessChecks.exists(m);
	BusinessChecks.isTrue(workOrders.isEmpty(),
		"The mechanic has workOrders");
	BusinessChecks.isTrue(terminatedContracts.isEmpty(),
		"The mechanic has terminated contracts");
	BusinessChecks.isTrue(inForceContracts.isEmpty(),
		"The mechanic has in force contracts");
	repo.remove(m.get());
	return null;
    }

    private List<Contract> obtainContractsByState(List<Contract> contracts,
	    ContractState state) {
	List<Contract> terminatedContracts = new ArrayList<>();
	for (Contract c : contracts) {
	    if (c.getState().equals(state))
		terminatedContracts.add(c);
	}
	return terminatedContracts;
    }
}
