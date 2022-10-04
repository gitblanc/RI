/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService;

/**
 * @author UO285176
 *
 */
public class MechanicServiceImpl implements MechanicService {

	@Override
	public MechanicBLDto addMechanic(MechanicBLDto mechanic) throws BusinessException {
		return new AddMechanic(mechanic).execute();
	}

	@Override
	public void deleteMechanic(String idMechanic) throws BusinessException {
		DeleteMechanic dm = new DeleteMechanic(idMechanic);
		dm.execute();

	}

	@Override
	public void updateMechanic(MechanicBLDto mechanic) throws BusinessException {
		UpdateMechanic um = new UpdateMechanic(mechanic);
		um.execute();

	}

	@Override
	public Optional<MechanicBLDto> findMechanicById(String idMechanic) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<MechanicBLDto> findMechanicByDni(String dniMechanic) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MechanicBLDto> findAllMechanics() throws BusinessException {
		return new FindAllMechanics().execute();
	}

}
