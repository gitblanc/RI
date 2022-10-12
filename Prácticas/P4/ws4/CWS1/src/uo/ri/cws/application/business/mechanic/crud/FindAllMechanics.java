/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

/**
 * @author UO285176
 *
 */
public class FindAllMechanics implements Command<List<MechanicBLDto>> {

	public List<MechanicBLDto> execute() {
		List<MechanicBLDto> mechanics = new ArrayList<>();
		MechanicGateway mg = PersistenceFactory.forMechanic();

		mechanics = MechanicAssembler.toDtoList(mg.findAll());// no hay nada que comprobar en un findall
		return mechanics;
	}
}
