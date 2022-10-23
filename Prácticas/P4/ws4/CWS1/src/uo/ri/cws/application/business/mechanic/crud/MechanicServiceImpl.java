/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.util.command.CommandExecutor;

/**
 * @author UO285176
 *
 */
public class MechanicServiceImpl implements MechanicService {

	CommandExecutor executor = new CommandExecutor();

	@Override
	public MechanicBLDto addMechanic(MechanicBLDto mechanic) throws BusinessException {
		return executor.execute(new AddMechanic(mechanic));// el executor llama al execute del addMechanic
	}

	@Override
	public void deleteMechanic(String idMechanic) throws BusinessException {
		executor.execute(new DeleteMechanic(idMechanic));

	}

	@Override
	public void updateMechanic(MechanicBLDto mechanic) throws BusinessException {
		executor.execute(new UpdateMechanic(mechanic));

	}

	@Override
	public Optional<MechanicBLDto> findMechanicById(String idMechanic) throws BusinessException {
		return executor.execute(new FindMechanicById(idMechanic));
	}

	@Override
	public Optional<MechanicBLDto> findMechanicByDni(String dniMechanic) throws BusinessException {
		return executor.execute(new FindMechanicByDni(dniMechanic));
	}

	@Override
	public List<MechanicBLDto> findAllMechanics() throws BusinessException {
		return executor.execute(new FindAllMechanics()); // patron comando
	}

	@Override
	public List<MechanicBLDto> findMechanicsInForce() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MechanicBLDto> findMechanicsWithContractInForceInContractType(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MechanicBLDto> findMechanicsInProfessionalGroups(String string) {
		// TODO Auto-generated method stub
		return null;
	}

}
