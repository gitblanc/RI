/**
 * 
 */
package uo.ri.cws.application.persistence.payroll.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.persistence.util.Conf;

/**
 * @author UO285176
 *
 */
public class PayrollGatewayImpl implements PayrollGateway {

	@Override
	public void add(PayrollDALDto payroll) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TPAYROLLS_add"));
			pst.setString(1, payroll.id);
			pst.setDouble(2, payroll.bonus);
			pst.setDate(3, Date.valueOf(payroll.date));
			pst.setDouble(4, payroll.incomeTax);
			pst.setDouble(5, payroll.monthlyWage);
			pst.setDouble(6, payroll.nic);
			pst.setDouble(7, payroll.productivityBonus);
			pst.setDouble(8, payroll.trienniumPayment);
			pst.setLong(9, payroll.version);
			pst.setString(10, payroll.contractId);

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
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TPAYROLLS_remove"));
			pst.setString(1, id);

			pst.executeUpdate();

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
	}

	@Override
	public void update(PayrollDALDto t) {

	}

	@Override
	public Optional<PayrollDALDto> findById(String id) {
		Optional<PayrollDALDto> payroll = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TPAYROLLS_findById"));
			pst.setString(1, id);
			rs = pst.executeQuery();

			payroll = PayrollAssembler.toPayrollDALDto(rs);// Fijarse en que sea
															// el Assembler de
															// persistence y no
															// de
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
		return payroll;
	}

	@Override
	public List<PayrollDALDto> findAll() {
		List<PayrollDALDto> payrolls = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TPAYROLLS_findAll"));

			rs = pst.executeQuery();
			payrolls = PayrollAssembler.toPayrollDALDtoList(rs);
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
		return payrolls;
	}

	@Override
	public List<PayrollDALDto> findByContractId(String cId) {
		List<PayrollDALDto> payroll = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance()
					.getProperty("TPAYROLLS_findByContractId"));
			pst.setString(1, cId);
			rs = pst.executeQuery();

			payroll = PayrollAssembler.toPayrollDALDtoList(rs);// Fijarse en que
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
		return payroll;
	}

	@Override
	public List<PayrollSummaryDALDto> findAllSummary() {
		List<PayrollSummaryDALDto> payrolls = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(
					Conf.getInstance().getProperty("TPAYROLLS_findAll"));

			rs = pst.executeQuery();
			payrolls = PayrollAssembler.toPayrollDALDtoListSummary(rs);
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
		return payrolls;
	}

}
