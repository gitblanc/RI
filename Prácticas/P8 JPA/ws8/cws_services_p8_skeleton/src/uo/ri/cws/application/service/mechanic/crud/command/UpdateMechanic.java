package uo.ri.cws.application.service.mechanic.crud.command;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;

public class UpdateMechanic {

	private MechanicDto dto;

	public UpdateMechanic(MechanicDto dto) {
		this.dto = dto;
	}

	public Void execute() throws BusinessException {

		return null;
	}

}
