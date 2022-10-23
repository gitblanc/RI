/**
 * 
 */
package uo.ri.cws.application.persistence.contract.assembler;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractSummaryDALDto;

/**
 * @author UO285176
 *
 */
public class ContractAssembler {
	public static Optional<ContractSummaryDALDto> toContractSummaryDALDto(ResultSet c) throws SQLException {
		if (c.next()) {
			return Optional.of(resultSetToContractSummaryDALDto(c));
		} else
			return Optional.ofNullable(null);
	}

	public static List<ContractSummaryDALDto> toContractSummaryListDALDto(ResultSet rs) throws SQLException {
		List<ContractSummaryDALDto> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToContractSummaryDALDto(rs));
		}

		return res;
	}

	private static ContractSummaryDALDto resultSetToContractSummaryDALDto(ResultSet rs) throws SQLException {
		ContractSummaryDALDto value = new ContractSummaryDALDto();

		value.id = rs.getString("id");
		value.version = rs.getLong("version");

		value.dni = rs.getString("dni");
		value.settlement = rs.getDouble("settlement");
		if (rs.getString("state").equals("TERMINATED"))
			value.state = ContractState.TERMINATED;
		else
			value.state = ContractState.IN_FORCE;
		return value;
	}

	public static Optional<ContractDALDto> toContractDALDto(ResultSet m) throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToContractDALDto(m));
		} else
			return Optional.ofNullable(null);
	}

	private static ContractDALDto resultSetToContractDALDto(ResultSet rs) throws SQLException {
		ContractDALDto value = new ContractDALDto();

		value.id = rs.getString("id");
		value.version = rs.getLong("version");
		value.annualBaseWage = rs.getDouble("annualbasewage");
		value.contractTypeName = rs.getString("contracttype_id");
		value.endDate = rs.getDate("enddate").toLocalDate();
		value.startDate = rs.getDate("startdate").toLocalDate();
		value.professionalGroupName = rs.getString("professionalgroup_id");
		value.dni = rs.getString("mechanic_id");
		value.settlement = rs.getDouble("settlement");
		if (rs.getString("state").equals("TERMINATED"))
			value.state = ContractState.TERMINATED;
		else
			value.state = ContractState.IN_FORCE;
		return value;
	}
}
