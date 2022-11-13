package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class FindMechanicById implements Command<Optional<MechanicDto>> {

	private String id;

	public FindMechanicById(String id) {
		ArgumentChecks.isNotNull(id, "The mechanic id can't be null");
		ArgumentChecks.isNotEmpty(id, "The mechanic id can't be empty");
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {
		Optional<Mechanic> m = Factory.repository.forMechanic().findById(id);
		return Optional.of(DtoAssembler.toDto(m.get()));
	}

}
