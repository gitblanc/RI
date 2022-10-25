/**
 * 
 */
package uo.ri.cws.application.persistence.contract.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.assembler.ContractAssembler;
import uo.ri.cws.application.persistence.util.Conf;

/**
 * @author UO285176
 *
 */
public class ContractGatewayImpl implements ContractGateway {

	@Override
	public List<ContractSummaryDALDto> findContractsByDni(String dni) {
		List<ContractSummaryDALDto> contracts = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TCONTRACTS_findByDni"));
			pst.setString(1, dni);
			rs = pst.executeQuery();

			contracts = ContractAssembler.toContractSummaryListDALDto(rs);// Fijarse
																			// en
																			// que
																			// sea
																			// el
																			// Assembler
																			// de
																			// persistence
																			// y
			// no de
			// business

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
		return contracts;
	}

	@Override
	public Optional<ContractDALDto> findContractByProfessionalGroup(String id) {
		Optional<ContractDALDto> contracts = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TCONTRACTS_findByGroupId"));
			pst.setString(1, id);
			rs = pst.executeQuery();

			contracts = ContractAssembler.toContractDALDto(rs);// Fijarse en que
																// sea el
																// Assembler de
																// persistence y
			// no de
			// business

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
		return contracts;
	}

	@Override
	public void add(ContractDALDto contract) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TCONTRACTS_add"));
			pst.setString(1, contract.id);
			pst.setLong(2, contract.version);
			pst.setString(3, contract.dni);
			pst.setString(4, contract.contractTypeName);
			pst.setString(5, contract.professionalGroupName);
			pst.setDate(6, Date.valueOf(contract.startDate));
			pst.setDate(7, Date.valueOf(contract.endDate));
			pst.setDouble(8, contract.annualBaseWage);
			pst.setDouble(9, contract.settlement);
			pst.setString(10, contract.state.name());

			pst.executeUpdate();

		} catch (SQLException e) {
			throw new PersistenceException("Database error");// Esto hay que
																// hacerlo en
																// todos los
																// errores de
																// persistencia
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
	}

	@Override
	public void remove(String id) {

	}

	@Override
	public void update(ContractDALDto t) {

	}

	@Override
	public Optional<ContractDALDto> findById(String id) {
		return null;
	}

	@Override
	public List<ContractDALDto> findAll() {
		return null;
	}

	@Override
	public List<ContractDALDto> findContractsInForce(String id) {
		List<ContractDALDto> contracts = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("TCONTRACTS_findContractsInForce"));
			pst.setString(1, id);
			rs = pst.executeQuery();
			contracts = ContractAssembler.toContractListDALDto(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Database error");// Esto hay que
																// hacerlo en
																// todos los
																// errores de
			// persistencia
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
		return contracts;
	}

	@Override
	public Optional<ContractDALDto> findContractInForceById(String id) {
		Optional<ContractDALDto> contract = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("TCONTRACTS_findContractsInForceById"));
			//Date pastMonthDate = LocalDate.now()
			pst.setString(1, id);
			rs = pst.executeQuery();

			contract = ContractAssembler.toContractDALDto(rs);// Fijarse en que
																// sea el
																// Assembler de
																// persistence y
																// no de
																// business

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
