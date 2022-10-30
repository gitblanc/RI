/**
 * 
 */
package uo.ri.cws.application.persistence.contracttype.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.assembler.ContractTypeAssembler;
import uo.ri.cws.application.persistence.util.Conf;

/**
 * @author UO285176
 *
 */
public class ContractTypeGatewayImpl implements ContractTypeGateway {

	@Override
	public void add(ContractTypeDALDto t) {
	}

	@Override
	public void remove(String id) {
		
		
	}

	@Override
	public void update(ContractTypeDALDto t) {
		
		
	}

	@Override
	public Optional<ContractTypeDALDto> findById(String id) {
		
		return null;
	}

	@Override
	public List<ContractTypeDALDto> findAll() {
		
		return null;
	}

	@Override
	public Optional<ContractTypeDALDto> findByName(String name) {
		Optional<ContractTypeDALDto> contract = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TCONTRACTTYPE_findByName"));
			pst.setString(1, name);
			rs = pst.executeQuery();

			contract = ContractTypeAssembler.toContractTypeDALDto(rs);

		} catch (SQLException e) {
			throw new PersistenceException("Database error");
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
					/* ignore */ }
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					/* ignore */ }

		}
		return contract;
	}
}