/**
 * 
 */
package uo.ri.cws.application.persistence.contract.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.contract.ContractService.ContractState;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;

/**
 * @author UO285176
 *
 */
public class ContractAssembler {
	public static Optional<ContractDALDto> toContractDALDto(ResultSet c) throws SQLException {
		if (c.next()) {
			return Optional.of(resultSetToContractDALDto(c));
		} else
			return Optional.ofNullable(null);
	}
	
	public static List<ContractDALDto> toContractListDALDto(ResultSet rs) throws SQLException {
		List<ContractDALDto> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToContractDALDto(rs));
		}

		return res;
	}

	private static ContractDALDto resultSetToContractDALDto(ResultSet rs) throws SQLException {
		ContractDALDto value = new ContractDALDto();

		value.id = rs.getString("id");
		value.version = rs.getLong("version");

		value.dni = rs.getString("dni");
		value.contractTypeName = rs.getString("contractTypeName");
		value.professionalGroupName = rs.getString("professionalGroupName");
		value.startDate = rs.getDate("startDate").toLocalDate();
		value.endDate = rs.getDate("endDate").toLocalDate();
		value.annualBaseWage = rs.getDouble("annualBaseWage");
		value.settlement = rs.getDouble("settlement");
		if (rs.getString("state").equals("TERMINATED"))
			value.state = ContractState.TERMINATED;
		else
			value.state = ContractState.IN_FORCE;
		return value;
	}
}
