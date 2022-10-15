/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

/**
 * @author UO285176
 *
 */
public class UpdateMechanic implements Command<MechanicBLDto> {

	private MechanicBLDto mechanic = null;

	public UpdateMechanic(MechanicBLDto mechanic) {
		Argument.isNotNull(mechanic, "There can't be a null mechanic");
		Argument.isNotEmpty(mechanic.id, "The id of the mechanic can't be empty");
		Argument.isNotEmpty(mechanic.dni, "The dni of the mechanic can't be empty");
		Argument.isNotEmpty(mechanic.name, "The name of the mechanic can't be empty");
		Argument.isNotEmpty(mechanic.surname, "The surname of the mechanic can't be empty");
		Argument.isNotNull(mechanic.id, "The id of the mechanic can't be null");
		Argument.isNotNull(mechanic.dni, "The dni of the mechanic can't be null");
		Argument.isNotNull(mechanic.name, "The name of the mechanic can't be null");
		Argument.isNotNull(mechanic.surname, "The surname of the mechanic can't be null");
		this.mechanic = mechanic;
	}

	@Override
	public MechanicBLDto execute() throws BusinessException {
		MechanicGateway mg = PersistenceFactory.forMechanic();
		// Comprobación de que el mecánico existe
		BusinessCheck.isTrue(!mg.findById(mechanic.id).isEmpty(), "The mechanic doen't exist");
		mg.update(MechanicAssembler.toDALDto(mechanic));
		
		return mechanic;
	}

}
