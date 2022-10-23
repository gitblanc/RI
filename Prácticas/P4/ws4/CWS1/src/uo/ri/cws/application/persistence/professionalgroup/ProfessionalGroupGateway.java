/**
 * 
 */
package uo.ri.cws.application.persistence.professionalgroup;

import java.util.Optional;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

/**
 * @author UO285176
 *
 */
public interface ProfessionalGroupGateway extends Gateway<ProfessionalGroupDALDto> {

	public class ProfessionalGroupDALDto {
		public String id;
		public long version;

		public String name;
		public double trieniumSalary;
		public double productivityRate;

	}

	public Optional<ProfessionalGroupDALDto> findByName(String name);
}
