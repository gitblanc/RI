/**
 * 
 */
package uo.ri.cws.application.business.contract.assembler;

import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

/**
 * @author UO285176
 *
 */
public class ContractAssembler {
	@SuppressWarnings("unlikely-arg-type")
	public static ContractDALDto toDALDto(ContractBLDto c) {
		ContractDALDto value = new ContractDALDto();
		value.id = c.id;
		value.version = c.version;
		value.dni = c.dni;
		value.contractTypeName = c.contractTypeName;
		value.professionalGroupName = c.professionalGroupName;
		value.startDate = c.startDate;
		value.endDate = c.endDate;
		value.annualBaseWage = c.annualBaseWage;
		value.settlement = c.settlement;
		if (c.state.equals("TERMINATED"))
			value.state = ContractState.TERMINATED;
		else
			value.state = ContractState.IN_FORCE;
		return value;
	}
}
