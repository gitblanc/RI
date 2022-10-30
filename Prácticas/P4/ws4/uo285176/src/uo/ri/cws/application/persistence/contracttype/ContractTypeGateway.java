/**
 * 
 */
package uo.ri.cws.application.persistence.contracttype;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto;;

/**
 * @author UO285176
 *
 */
public interface ContractTypeGateway extends Gateway<ContractTypeDALDto> {

	public Optional<ContractTypeDALDto> findByName(String name);

	public class ContractTypeDALDto {
		public String id;
		public Long version;
		public String name;
		public double compensationDays;
	}
}
