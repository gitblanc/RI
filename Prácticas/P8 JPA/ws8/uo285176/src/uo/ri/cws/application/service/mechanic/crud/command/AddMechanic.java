package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class AddMechanic implements Command<MechanicDto> {

	private MechanicDto dto;

	public AddMechanic(MechanicDto dto) {
		ArgumentChecks.isNotNull(dto, "The mechanic can't be null");
		ArgumentChecks.isNotNull(dto.dni, "The mechanic dni can't be null");
		ArgumentChecks.isNotEmpty(dto.dni, "The mechanic dni can't be empty");
		ArgumentChecks.isNotBlank(dto.dni, "The mechanic dni can't be blank");
		ArgumentChecks.isNotNull(dto.name, "The mechanic name can't be null");
		ArgumentChecks.isNotEmpty(dto.name, "The mechanic name can't be empty");
		ArgumentChecks.isNotBlank(dto.name, "The mechanic name can't be blank");
		ArgumentChecks.isNotNull(dto.surname, "The mechanic surname can't be null");
		ArgumentChecks.isNotEmpty(dto.surname, "The mechanic surname can't be empty");
		ArgumentChecks.isNotBlank(dto.surname, "The mechanic surname can't be blank");
		this.dto = dto;
	}

	public MechanicDto execute() throws BusinessException {
		Optional<Mechanic> me = Factory.repository.forMechanic().findByDni(dto.dni);
		BusinessChecks.isTrue(!me.isEmpty(), "There can't be two mechanics with same dni");

		// el emf ha de hablar con la bbdd y guardar la informacion
		Mechanic m = new Mechanic(dto.dni, dto.name, dto.surname);
		Factory.repository.forMechanic().add(m);
		dto.id = m.getId();

		return dto;
	}

	/**
	 * Si salen errores con consultas es pq falta por quitar los @Transient modelo
	 * dominio
	 */

}
