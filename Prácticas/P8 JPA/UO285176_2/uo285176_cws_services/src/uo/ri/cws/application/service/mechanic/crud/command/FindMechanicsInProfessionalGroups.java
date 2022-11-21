/**
 * 
 */
package uo.ri.cws.application.service.mechanic.crud.command;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class FindMechanicsInProfessionalGroups
	implements Command<List<MechanicDto>> {

    private String group = null;
    private MechanicRepository repo = Factory.repository.forMechanic();
    private ProfessionalGroupRepository prepo = Factory.repository
	    .forProfessionalGroup();

    public FindMechanicsInProfessionalGroups(String group) {
	ArgumentChecks.isNotNull(group, "The group can't be null");
	ArgumentChecks.isNotEmpty(group, "The group can't be empty");
	ArgumentChecks.isNotBlank(group, "The group can't be blank");
	this.group = group;
    }

    @Override
    public List<MechanicDto> execute() throws BusinessException {
	Optional<ProfessionalGroup> g = prepo.findByName(group);

	if (g.isPresent())
	    return DtoAssembler.toMechanicDtoList(
		    repo.findAllInProfessionalGroup(g.get()));
	return new ArrayList<>();
    }

}
