/**
 * 
 */
package uo.ri.cws.application.business.professionalgroup.crud;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;

/**
 * @author UO285176
 *
 */
public class UpdateProfessionalGroup implements Command<ProfessionalGroupBLDto> {

	ProfessionalGroupBLDto group = null;

	public UpdateProfessionalGroup(ProfessionalGroupBLDto dto) {
		Argument.isNotNull(dto, "The group can't be null");
		Argument.isNotNull(dto.name, "The group name can't be null");
		Argument.isNotEmpty(dto.name, "The group name can't be empty");
		Argument.isTrue(dto.trieniumSalary > 0, "The salary can't be negative");
		Argument.isTrue(dto.productivityRate > 0, "The plus can't be negative");
		this.group = dto;
	}

	@Override
	public ProfessionalGroupBLDto execute() throws BusinessException {
		ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
		BusinessCheck.isTrue(!pg.findByName(group.name).isEmpty(), "The group must exist");
		pg.update(ProfessionalGroupAssembler.toDALDto(group));
		return null;
	}

}
