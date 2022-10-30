/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

/**
 * @author UO285176
 *
 */
public class FindMechanicByDni implements Command<Optional<MechanicBLDto>> {

	String dni = null;
	
	public FindMechanicByDni(String dni) {
		Argument.isNotNull(dni, "The mechanic dni can't be null");
		Argument.isNotEmpty(dni, "The mechanic dni can't be empty");
		this.dni = dni;
	}

	@Override
	public Optional<MechanicBLDto> execute(){
		MechanicGateway mg = PersistenceFactory.forMechanic();
		return MechanicAssembler.toBLDto(mg.findByDni(dni));
	}

}
