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
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto;

/**
 * @author UO285176
 *
 */
public class FindMechanicsInForce implements Command<List<MechanicBLDto>> {

	public List<MechanicBLDto> execute() {
		MechanicGateway mg = PersistenceFactory.forMechanic();
		return findMechanicsWithContractsInForce(mg.findAll());
	}

	private List<MechanicBLDto> findMechanicsWithContractsInForce(
			List<MechanicDALDto> allMechanics) {
		List<MechanicBLDto> mechanics = new ArrayList<>();
		for (MechanicDALDto m : allMechanics) {
			if (checkContractInForce(m))
				mechanics.add(MechanicAssembler.toBLDto(m));
		}
		return mechanics;
	}

	private boolean checkContractInForce(MechanicDALDto m) {
		ContractGateway cg = PersistenceFactory.forContract();
		return !cg.findContractInForceById(m.id).isEmpty() ? true : false;
	}

}
