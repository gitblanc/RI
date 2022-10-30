/**
 * 
 */
package uo.ri.cws.application.persistence.professionalgroup.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;

/**
 * @author UO285176
 *
 */
public class ProfessionalGroupAssembler {
	public static Optional<ProfessionalGroupDALDto> toProfessionalGroupDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToProfessionalGroupDALDto(m));
		} else
			return Optional.ofNullable(null);
	}

	private static ProfessionalGroupDALDto resultSetToProfessionalGroupDALDto(ResultSet rs) throws SQLException {
		ProfessionalGroupDALDto group = new ProfessionalGroupDALDto();
		group.id = rs.getString("id");
		group.name = rs.getString("name");
		group.productivityRate = rs.getDouble("PRODUCTIVITYBONUSPERCENTAGE");
		group.trieniumSalary = rs.getDouble("TRIENNIUMPAYMENT");
		group.version = rs.getLong("VERSION");
		return group;
	}

	public static List<ProfessionalGroupDALDto> toProfessionalGroupDALDtoList(ResultSet rs) throws SQLException {
		List<ProfessionalGroupDALDto> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToProfessionalGroupDALDto(rs));
		}

		return res;
	}
}
