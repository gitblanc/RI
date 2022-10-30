/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

/**
 * @author UO285176
 *
 */
public class FindMechanicsInProfessionalGroup
		implements Command<List<MechanicBLDto>> {

	String name = null;

	public FindMechanicsInProfessionalGroup(String name) {
		Argument.isNotNull(name, "The name can't be null");
		Argument.isNotEmpty(name, "The name can't be empty");
		this.name = name;
	}

	public List<MechanicBLDto> execute(){
		List<MechanicBLDto> mechanics = new ArrayList<>();
		ContractGateway cg = PersistenceFactory.forContract();
		ProfessionalGroupGateway pg = PersistenceFactory.forProfessionalGroup();
		// Si existe el grupo profesional
		Optional<ProfessionalGroupDALDto> group = pg.findByName(name);
		if (!group.isEmpty()) {
			// Buscamos los contratos de ese grupo profesional
			List<ContractDALDto> contracts = cg
					.findContractsByProfessionalGroup(group.get().id);
			// Si hay contratos
			if (!contracts.isEmpty()) {
				mechanics = addMechanics(contracts);
			}
		}
		return mechanics;
	}

	private List<MechanicBLDto> addMechanics(List<ContractDALDto> contracts) {
		List<MechanicBLDto> mechanics = new ArrayList<>();
		MechanicGateway mg = PersistenceFactory.forMechanic();
		for (ContractDALDto c : contracts) {
			mechanics.add(MechanicAssembler.toBLDto(mg.findById(c.dni).get()));
		}
		return mechanics;
	}

}
