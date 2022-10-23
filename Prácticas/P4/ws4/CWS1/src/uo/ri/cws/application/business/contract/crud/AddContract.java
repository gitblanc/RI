/**
 * 
 */
package uo.ri.cws.application.business.contract.crud;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.assembler.ContractAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;

/**
 * @author UO285176
 *
 */
public class AddContract implements Command<ContractBLDto> {

	ContractBLDto contract = null;

	public AddContract(ContractBLDto contract) {
		Argument.isNotNull(contract, "The contract can't be null");
		Argument.isNotNull(contract.dni, "The contract mechanic dni can't be null");
		Argument.isNotNull(contract.contractTypeName, "The contract type name can't be null");
		Argument.isNotNull(contract.professionalGroupName, "The contract professional group name can't be null");
		Argument.isNotNull(contract.annualBaseWage, "The contract annual base wage can't be null");
		Argument.isNotEmpty(contract.dni, "The contract mechanic dni can't be empty");
		Argument.isNotEmpty(contract.contractTypeName, "The contract type name can't be empty");
		Argument.isNotEmpty(contract.professionalGroupName, "The contract professional group name can't be empty");
		Argument.isTrue(contract.annualBaseWage > 0, "The contract annual base wage can't be less than 0");
		if (contract.professionalGroupName.equals("FIXED_TERM")) {
			Argument.isNotNull(contract.endDate, "The date on FIXED_TERM can't be null");
		}
		this.contract = contract;
	}

	@Override
	public ContractBLDto execute() throws BusinessException {
		checkContractType();//FALTA
		checkMechanic();//FALTA
		checkProfessionalGroup();//FALTA
		checkEndDate();//FALTA
		ContractGateway cg = PersistenceFactory.forContract();
		cg.add(ContractAssembler.toDALDto(contract));
		return contract;
	}

	private void checkEndDate() {
		
	}

	private void checkProfessionalGroup() {
		
	}

	private void checkMechanic() {
		
	}

	private void checkContractType() {
		
		
	}

}
