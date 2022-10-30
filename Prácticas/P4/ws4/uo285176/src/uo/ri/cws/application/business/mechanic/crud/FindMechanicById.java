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
public class FindMechanicById implements Command<Optional<MechanicBLDto>> {

	String id = null;
	
	public FindMechanicById(String id) {
		Argument.isNotNull(id, "The mechanic id can't be null");
		Argument.isNotEmpty(id, "The mechanic id can't be empty");
		this.id = id;
	}

	@Override
	public Optional<MechanicBLDto> execute(){
		MechanicGateway mg = PersistenceFactory.forMechanic();
		return MechanicAssembler.toBLDto(mg.findById(id));
	}

}
