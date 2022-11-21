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
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class AddProfessionalGroup implements Command<ProfessionalGroupBLDto> {

    private ProfessionalGroupBLDto group = null;
    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();

    public AddProfessionalGroup(ProfessionalGroupBLDto dto) {
	ArgumentChecks.isNotNull(dto, "The group can't be null");
	ArgumentChecks.isNotNull(dto.name, "The group name can't be null");
	ArgumentChecks.isNotEmpty(dto.name, "The group name can't be empty");
	ArgumentChecks.isNotBlank(dto.name, "The group name can't be blank");
	ArgumentChecks.isTrue(dto.trieniumSalary >= 0,
		"The triennium must be positive");
	ArgumentChecks.isTrue(dto.productivityRate >= 0,
		"The triennium must be positive");
	this.group = dto;
    }

    @Override
    public ProfessionalGroupBLDto execute() throws BusinessException {
	Optional<ProfessionalGroup> pg = repo.findByName(group.name);
	BusinessChecks.isTrue(!pg.isPresent(), "The group already exists");
	ProfessionalGroup p = new ProfessionalGroup(group.name,
		group.trieniumSalary, group.productivityRate);
	repo.add(p);
	return DtoAssembler.toProfessionalGroupBLDto(p);
    }

}
