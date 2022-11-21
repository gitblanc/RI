package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteMechanic implements Command<Void> {

    private String mechanicId;

    private MechanicRepository repo = Factory.repository.forMechanic();

    public DeleteMechanic(String mechanicId) {
	ArgumentChecks.isNotNull(mechanicId, "The id can't be null");
	ArgumentChecks.isNotEmpty(mechanicId, "The id can't be empty");
	ArgumentChecks.isNotBlank(mechanicId, "The id can't be black string");
	this.mechanicId = mechanicId;
    }

    public Void execute() throws BusinessException {
	Optional<Mechanic> m = repo.findById(mechanicId);
	BusinessChecks.exists(m);
	BusinessChecks.isTrue(m.get().getAssigned().isEmpty(),
		"The mechanic has workOrders");
	BusinessChecks.isTrue(m.get().getTerminatedContracts().isEmpty(),
		"The mechanic has terminated contracts");
	BusinessChecks.isTrue(m.get().getContractInForce().isEmpty(),
		"The mechanic has in force contracts");
	repo.remove(m.get());
	return null;
    }
}
