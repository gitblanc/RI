/**
 * 
 */
package uo.ri.cws.application.service.professionalgroup.crud;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

/**
 * @author UO285176
 *
 */
public class FindAllProfessionalGroups
	implements Command<List<ProfessionalGroupBLDto>> {

    private ProfessionalGroupRepository repo = Factory.repository
	    .forProfessionalGroup();

    @Override
    public List<ProfessionalGroupBLDto> execute() throws BusinessException {
	return DtoAssembler.toProfessionalGroupBLDtoList(repo.findAll());
    }

}
