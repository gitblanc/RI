/**
 * 
 */
package uo.ri.cws.application.business.professionalgroup.crud;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

/**
 * @author UO285176
 *
 */
public class RemoveProfessionalGroup
		implements Command<ProfessionalGroupBLDto> {

	String name = null;

	public RemoveProfessionalGroup(String name) {
		Argument.isNotNull(name, "The name of the group can't be null");
		Argument.isNotEmpty(name, "The name of the group can't be empty");
		this.name = name;
	}

	@Override
	public ProfessionalGroupBLDto execute() throws BusinessException {
		ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
		ContractGateway cg = PersistenceFactory.forContract();
		Optional<ProfessionalGroupDALDto> g = pg.findByName(name);
		BusinessCheck.isTrue(g != null && !g.isEmpty(),
				"The group doesn't exist");
		BusinessCheck.isTrue(
				cg.findContractsByProfessionalGroup(g.get().id).isEmpty(),
				"The group has contracts assigned");
		pg.remove(g.get().id);
		return null;
	}

}
