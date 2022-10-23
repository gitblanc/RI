/**
 * 
 */
package uo.ri.cws.application.business.contract.assembler;

import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.contract.ContractService.ContractBLDto;
import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.business.contract.ContractService.ContractSummaryBLDto;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractSummaryDALDto;

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

	public static List<ContractBLDto> toDtoList(List<ContractDALDto> contracts) {
		List<ContractBLDto> result = new ArrayList<ContractBLDto>();
		for (ContractDALDto mr : contracts)
			result.add(toContractDto(mr));
		return result;
	}

	@SuppressWarnings("unlikely-arg-type")
	private static ContractBLDto toContractDto(ContractDALDto c) {

		ContractBLDto value = new ContractBLDto();
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

	public static List<ContractSummaryBLDto> toContractSummaryDtoList(List<ContractSummaryDALDto> contracts) {
		List<ContractSummaryBLDto> result = new ArrayList<ContractSummaryBLDto>();
		for (ContractSummaryDALDto mr : contracts)
			result.add(toContractSummaryDto(mr));
		return result;
	}

	@SuppressWarnings("unlikely-arg-type")
	private static ContractSummaryBLDto toContractSummaryDto(ContractSummaryDALDto c) {
		ContractSummaryBLDto value = new ContractSummaryBLDto();
		value.id = c.id;
		value.version = c.version;
		value.dni = c.dni;
		value.settlement = c.settlement;
		if (c.state.equals("TERMINATED"))
			value.state = ContractState.TERMINATED;
		else
			value.state = ContractState.IN_FORCE;
		return value;
	}
}
