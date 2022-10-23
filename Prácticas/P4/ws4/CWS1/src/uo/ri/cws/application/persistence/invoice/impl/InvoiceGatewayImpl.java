/**
 * 
 */
package uo.ri.cws.application.persistence.invoice.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.util.Conf;

/**
 * @author UO285176
 *
 */
public class InvoiceGatewayImpl implements InvoiceGateway {
	@Override
	public void add(InvoiceDALDto invoice) {
		PreparedStatement pst = null;
		Connection c = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TINVOICES_addInvoice"));
			pst.setString(1, invoice.id);
			pst.setDouble(2, invoice.amount);
			pst.setDate(3, java.sql.Date.valueOf(invoice.date));
			pst.setLong(4, invoice.number);
			pst.setString(5, invoice.state);
			pst.setDouble(6, invoice.vat);
			pst.setLong(7, invoice.version);

			pst.executeUpdate();
//			c.commit();
		} catch (SQLException e) {
			throw new PersistenceException("Database error");
		} finally {
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
	public void update(InvoiceDALDto t) {
	}

	@Override
	public Optional<InvoiceDALDto> findById(String id) {
		return null;
	}

	@Override
	public List<InvoiceDALDto> findAll() {
		return null;
	}

	@Override
	public Optional<InvoiceDALDto> findByNumber(Long number) {
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection c = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TINVOICES_findLastInvoiceNumber"));
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
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
}
