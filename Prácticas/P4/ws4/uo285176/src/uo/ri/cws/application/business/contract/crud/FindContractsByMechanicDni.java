/**
 * 
 */
package uo.ri.cws.application.business.contract.crud;

import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractSummaryDALDto;

/**
 * @author UO285176
 *
 */
public class FindContractsByMechanicDni implements Command<List<ContractSummaryBLDto>>{

	String dni = null;
	
	public FindContractsByMechanicDni(String mechanicDni) {
		Argument.isNotNull(mechanicDni, "The mechanic dni can't be null");
		Argument.isNotNull(mechanicDni, "The mechanic dni can't be empty");
		this.dni = mechanicDni;
	}

	@Override
	public List<ContractSummaryBLDto> execute() throws BusinessException {
		ContractGateway cg = PersistenceFactory.forContract();
		List<ContractSummaryDALDto> contract = cg.findContractsByDni(dni);
		return ContractAssembler.toContractSummaryDtoList(contract);
	}

}
