/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.util.UUID;

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
public class AddMechanic implements Command<MechanicBLDto> {

	private MechanicBLDto mechanic = null;

	public AddMechanic(MechanicBLDto mechanic) {
		Argument.isNotNull(mechanic, "The mechanic cannot be null");
		Argument.isNotNull(mechanic.dni, "The mechanic dni cannot be null");
		Argument.isNotEmpty(mechanic.dni, "The mechanic dni cannot be empty");
		this.mechanic = mechanic;
		this.mechanic.id = UUID.randomUUID().toString();
		this.mechanic.version = 1L;
	}

	@Override
	// L�gica de negocio para a�adir a un mec�nico
	public MechanicBLDto execute() throws BusinessException {
		MechanicGateway mg = PersistenceFactory.forMechanic();
		// Comprobaci�n de que el mec�nico est� vac�o
		BusinessCheck.isTrue(mg.findByDni(mechanic.dni).isEmpty(), "There exists a mechanic with the same dni");
		mg.add(MechanicAssembler.toDALDto(mechanic));

		return mechanic;
	}

	

}
