package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class FindMechanicById {

	private String id;

	public FindMechanicById(String id) {
		this.id = id;
	}

	public Optional<MechanicDto> execute() throws BusinessException {

		return Optional.empty();
	}

}
