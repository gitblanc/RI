/**
 * 
 */
package uo.ri.cws.application.persistence.contract;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

/**
 * @author UO285176
 *
 */
public interface ContractGateway extends Gateway<ContractDALDto> {

	List<ContractSummaryDALDto> findContractsByDni(String dni);

	Optional<ContractDALDto> findContractByProfessionalGroup(String group);

	public class ContractDALDto {
		public String id;
		public long version;

		public String dni;
		public String contractTypeName;
		public String professionalGroupName;
		public LocalDate startDate;
		public LocalDate endDate;
		public double annualBaseWage;

		// Filled in reading operations
		public double settlement;
		public ContractState state;
	}

	public class ContractSummaryDALDto {

		public String id;
		public long version;

		public String dni;

		// Filled in reading operations
		public double settlement;
		public ContractState state;

		// For summary only
		public int numPayrolls;
	}
}
