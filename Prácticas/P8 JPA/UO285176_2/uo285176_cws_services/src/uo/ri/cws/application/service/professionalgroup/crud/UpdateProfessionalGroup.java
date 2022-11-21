/**
 * 
 */
package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class UpdateProfessionalGroup implements Command<Void> {

    private ProfessionalGroupBLDto group = null;

    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();

    public UpdateProfessionalGroup(ProfessionalGroupBLDto dto) {
	ArgumentChecks.isNotNull(dto);
	ArgumentChecks.isNotNull(dto.name);
	ArgumentChecks.isNotEmpty(dto.name);
	ArgumentChecks.isNotBlank(dto.name);
	ArgumentChecks.isTrue(dto.trieniumSalary >= 0,
		"The triennium must be positive");
	ArgumentChecks.isTrue(dto.productivityRate >= 0,
		"The triennium must be positive");
	this.group = dto;
    }

    @Override
    public Void execute() throws BusinessException {
	Optional<ProfessionalGroup> g = repo.findById(group.id);
	BusinessChecks.isTrue(g.isPresent(), "It must exist");
	ProfessionalGroup newGroup = g.get();

	BusinessChecks.hasVersion(newGroup, group.version);
	newGroup.setName(group.name);
	newGroup.setProductivityRate(group.productivityRate);
	newGroup.setTriennium(group.trieniumSalary);
	return null;
    }

}
