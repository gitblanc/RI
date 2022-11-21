package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Mechanic;
import uo.ri.util.assertion.ArgumentChecks;

public class UpdateMechanic implements Command<Void> {

    private MechanicDto dto;
    private MechanicRepository repo = Factory.repository.forMechanic();

    public UpdateMechanic(MechanicDto dto) {
	ArgumentChecks.isNotNull(dto, "The mechanic can't be null");
	ArgumentChecks.isNotNull(dto.dni, "The mechanic dni can't be null");
	ArgumentChecks.isNotEmpty(dto.dni, "The mechanic dni can't be empty");
	ArgumentChecks.isNotBlank(dto.dni, "The mechanic dni can't be blank");
	ArgumentChecks.isNotNull(dto.name, "The mechanic name can't be null");
	ArgumentChecks.isNotEmpty(dto.name, "The mechanic name can't be empty");
	ArgumentChecks.isNotBlank(dto.name, "The mechanic name can't be blank");
	ArgumentChecks.isNotNull(dto.surname,
		"The mechanic surname can't be null");
	ArgumentChecks.isNotEmpty(dto.surname,
		"The mechanic surname can't be empty");
	ArgumentChecks.isNotBlank(dto.surname,
		"The mechanic surname can't be blank");
	this.dto = dto;
    }

    public Void execute() throws BusinessException {
	Optional<Mechanic> m = repo.findById(dto.id);
	BusinessChecks.isTrue(m.isPresent(), "The mechanic must exist");
	Mechanic me = m.get();

	BusinessChecks.hasVersion(me, dto.version);// IMPORTANTE control de
						   // versiones

	me.setName(dto.name);
	me.setSurname(dto.surname);
	return null;
    }

}
