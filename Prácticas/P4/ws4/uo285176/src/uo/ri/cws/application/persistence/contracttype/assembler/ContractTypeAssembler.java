/**
 * 
 */
package uo.ri.cws.application.persistence.contracttype.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway.ContractTypeDALDto;

/**
 * @author UO285176
 *
 */
public class ContractTypeAssembler {

	public static Optional<ContractTypeDALDto> toContractTypeDALDto(ResultSet m)
			throws SQLException {
		if (m.next()) {
			return Optional.of(resultSetToContractTypeDALDto(m));
		} else
			return Optional.ofNullable(null);
	}

	private static ContractTypeDALDto resultSetToContractTypeDALDto(ResultSet m)
			throws SQLException {
		ContractTypeDALDto c = new ContractTypeDALDto();
		c.id = m.getString("id");
		c.name = m.getString("name");
		c.version = m.getLong("version");
		c.compensationDays = m.getDouble("compensationdays");
		return c;
	}

}
