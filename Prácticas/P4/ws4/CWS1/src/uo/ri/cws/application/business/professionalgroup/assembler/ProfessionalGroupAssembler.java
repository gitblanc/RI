/**
 * 
 */
package uo.ri.cws.application.business.professionalgroup.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

/**
 * @author UO285176
 *
 */
public class ProfessionalGroupAssembler {

	public static ProfessionalGroupDALDto toDALDto(ProfessionalGroupBLDto arg) {
		ProfessionalGroupDALDto result = new ProfessionalGroupDALDto();
		result.id = arg.id;
		result.name = arg.name;
		result.productivityRate = arg.productivityRate;
		result.trieniumSalary = arg.trieniumSalary;
		result.version = arg.version;
		return result;
	}

	public static Optional<ProfessionalGroupBLDto> toDto(Optional<ProfessionalGroupDALDto> arg) {
		Optional<ProfessionalGroupBLDto> result = arg.isEmpty() ? Optional.ofNullable(null)
				: Optional.ofNullable(toProfessionalGroupBLDto(arg.get()));
		return result;
	}

	private static ProfessionalGroupBLDto toProfessionalGroupBLDto(ProfessionalGroupDALDto arg) {
		ProfessionalGroupBLDto result = new ProfessionalGroupBLDto();
		result.id = arg.id;
		result.name = arg.name;
		result.productivityRate = arg.productivityRate;
		result.trieniumSalary = arg.trieniumSalary;
		result.version = arg.version;
		return result;
	}

	public static List<ProfessionalGroupBLDto> toDtoList(List<ProfessionalGroupDALDto> arg) {
		List<ProfessionalGroupBLDto> result = new ArrayList<ProfessionalGroupBLDto>();
		for (ProfessionalGroupDALDto mr : arg)
			result.add(toProfessionalGroupBLDto(mr));
		return result;
	}
}
