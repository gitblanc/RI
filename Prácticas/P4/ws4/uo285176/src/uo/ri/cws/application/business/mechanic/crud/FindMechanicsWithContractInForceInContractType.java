package uo.ri.cws.application.business.mechanic.crud;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.mechanic.assembler.MechanicAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;

public class FindMechanicsWithContractInForceInContractType
		implements Command<List<MechanicBLDto>> {

	String type = null;

	public FindMechanicsWithContractInForceInContractType(String string) {
		Argument.isNotNull(string, "The type can't be null");
		Argument.isNotEmpty(string, "The type can't be empty");
		this.type = string;
	}

	@Override
	public List<MechanicBLDto> execute() throws BusinessException {
		List<MechanicBLDto> mechanics = new ArrayList<>();
		ContractGateway cg = PersistenceFactory.forContract();
		// Buscamos el tipo de contrato
		Optional<ContractTypeDALDto> contractType = PersistenceFactory
				.forContractType().findByName(type);
		if (!contractType.isEmpty()) {
			// Buscamos lod contratos del tipo concreto
			List<ContractDALDto> contracts = cg
					.findContractsByContractType(contractType.get().id);
			if (!contracts.isEmpty()) {
				MechanicGateway mg = PersistenceFactory.forMechanic();
				for (ContractDALDto c : contracts) {
					if (c.state.equals(ContractState.IN_FORCE)) {
						mechanics.add(MechanicAssembler
								.toBLDto(mg.findById(c.dni).get()));
					}
				}
			}
		}
		return mechanics;
	}

}
