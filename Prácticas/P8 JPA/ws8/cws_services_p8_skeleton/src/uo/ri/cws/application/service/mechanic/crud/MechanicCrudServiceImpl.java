package uo.ri.cws.application.service.mechanic.crud;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.crud.command.AddMechanic;
import uo.ri.cws.application.service.mechanic.crud.command.DeleteMechanic;
import uo.ri.cws.application.service.mechanic.crud.command.FindAllMechanics;
import uo.ri.cws.application.service.mechanic.crud.command.FindMechanicById;
import uo.ri.cws.application.service.mechanic.crud.command.UpdateMechanic;

public class MechanicCrudServiceImpl implements MechanicCrudService {
	
//	private CommandExecutor executor = Factory.executor.forExecutor();

	@Override
	public MechanicDto addMechanic(MechanicDto dto) throws BusinessException {
		return new AddMechanic( dto ).execute();
	}

	@Override
	public void updateMechanic(MechanicDto dto) throws BusinessException {
		new UpdateMechanic( dto ).execute();
	}

	@Override
	public void deleteMechanic(String iddto) throws BusinessException {
		new DeleteMechanic(iddto).execute();
	}

	@Override
	public List<MechanicDto> findAllMechanics() throws BusinessException {
		return new FindAllMechanics().execute();
	}

	@Override
	public Optional<MechanicDto> findMechanicById(String id) throws BusinessException {
		return new FindMechanicById(id).execute();
	}

}