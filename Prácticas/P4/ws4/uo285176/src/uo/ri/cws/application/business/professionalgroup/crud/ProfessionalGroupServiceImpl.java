/**
 * 
 */
package uo.ri.cws.application.business.professionalgroup.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService;
import uo.ri.cws.application.business.util.command.CommandExecutor;

/**
 * @author UO285176
 *
 */
public class ProfessionalGroupServiceImpl implements ProfessionalGroupService {

	CommandExecutor executor = new CommandExecutor();
	@Override
	public ProfessionalGroupBLDto addProfessionalGroup(ProfessionalGroupBLDto group) throws BusinessException {
		return executor.execute(new AddProfessionalGroup(group));
	}

	@Override
	public void deleteProfessionalGroup(String name) throws BusinessException {
		executor.execute(new RemoveProfessionalGroup(name));
	}

	@Override
	public void updateProfessionalGroup(ProfessionalGroupBLDto dto) throws BusinessException {
		executor.execute(new UpdateProfessionalGroup(dto));
	}

	@Override
	public Optional<ProfessionalGroupBLDto> findProfessionalGroupByName(String name) throws BusinessException {
		return executor.execute(new FindProfessionalGroupByName(name));
	}

	@Override
	public List<ProfessionalGroupBLDto> findAllProfessionalGroups() throws BusinessException {
		return executor.execute(new FindAllProfessionalGroups());
	}

}
