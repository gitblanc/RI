/**
 * 
 */
package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class FindProfessionalGroupByName
	implements Command<Optional<ProfessionalGroupBLDto>> {

    private String name = null;
    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();

    public FindProfessionalGroupByName(String name) {
	ArgumentChecks.isNotNull(name);
	ArgumentChecks.isNotEmpty(name);
	ArgumentChecks.isNotBlank(name);
	this.name = name;
    }

    @Override
    public Optional<ProfessionalGroupBLDto> execute() throws BusinessException {
	Optional<ProfessionalGroup> p = repo.findByName(name);
	return DtoAssembler.toProfessionalGroupDto(p);
    }

}
