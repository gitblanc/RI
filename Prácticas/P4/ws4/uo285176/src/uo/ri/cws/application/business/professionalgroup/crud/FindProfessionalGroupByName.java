/**
 * 
 */
package uo.ri.cws.application.business.professionalgroup.crud;

import java.util.Optional;

import assertion.Argument;
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
public class FindProfessionalGroupByName implements Command<Optional<ProfessionalGroupBLDto>> {

	String name = null;

	public FindProfessionalGroupByName(String name) {
		Argument.isNotNull(name, "The name can't be null");
		Argument.isNotEmpty(name, "The group name can't be empty");
		this.name = name;
	}

	@Override
	public Optional<ProfessionalGroupBLDto> execute() throws BusinessException {
		ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
		return ProfessionalGroupAssembler.toDto(pg.findByName(name));
	}

}
