/**
 * 
 */
package uo.ri.cws.application.business.professionalgroup.crud;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

/**
 * @author UO285176
 *
 */
public class FindAllProfessionalGroups implements Command<List<ProfessionalGroupBLDto>>{

	@Override
	public List<ProfessionalGroupBLDto> execute() throws BusinessException {
		ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
		return ProfessionalGroupAssembler.toDtoList(pg.findAll());
	}

}
