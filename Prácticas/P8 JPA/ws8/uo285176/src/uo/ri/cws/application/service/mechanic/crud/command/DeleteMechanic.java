package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class DeleteMechanic {

	private String mechanicId;

	private MechanicRepository repo = Factory.repository.forMechanic();

	public DeleteMechanic(String mechanicId) {
		ArgumentChecks.isNotNull(mechanicId, "The id can't be null");
		ArgumentChecks.isNotEmpty(mechanicId, "The id can't be empty");
		this.mechanicId = mechanicId;
	}

	public Void execute() throws BusinessException {
		Optional<Mechanic> m = repo.findById(mechanicId);
		BusinessChecks.exists(m);
		repo.remove(m.get());
		return null;
	}

}
