/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

/**
 * @author UO285176
 *
 */
public class FindMechanicsInForce implements Command<List<MechanicBLDto>> {

	public List<MechanicBLDto> execute() {
		List<MechanicBLDto> mechanics = new ArrayList<>();
		List<ContractBLDto> contracts = new ArrayList<>();
		ContractGateway cg = PersistenceFactory.forContract();
		contracts = ContractAssembler.toDtoList(cg.findContractsInForce());
		mechanics = crearListaMecanicos(contracts);
		return mechanics;
	}

	/**
	 * Método para crear la lista de mecánicos con contrato en estado IN_FORCE
	 * 
	 * @param contracts
	 * @return
	 */
	private List<MechanicBLDto> crearListaMecanicos(List<ContractBLDto> contracts) {
		List<MechanicBLDto> mechanics = new ArrayList<>();
		MechanicGateway mg = PersistenceFactory.forMechanic();
		for (ContractBLDto c : contracts) {
			mechanics.add(MechanicAssembler.toBLDto(mg.findByDni(c.dni)).get());
		}
		return mechanics;
	}
}
