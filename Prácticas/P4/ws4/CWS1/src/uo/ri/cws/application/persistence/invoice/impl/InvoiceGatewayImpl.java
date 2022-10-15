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
import uo.ri.cws.application.persistence.invoice.assembler.InvoiceAssembler;

/**
 * @author UO285176
 *
 */
public class InvoiceGatewayImpl implements InvoiceGateway {

	private static final String TINVOICES_findNotInvoicedWorkOrdersByDni = "select a.id, a.description, a.date, a.state, a.amount "
			+ "from TWorkOrders as a, TVehicles as v, TClients as c " + "where a.vehicle_id = v.id "
			+ "	and v.client_id = c.id " + "and state <> 'INVOICED'" + " and dni like ?";

	@Override
	public void add(InvoiceDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(InvoiceDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<InvoiceDALDto> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<InvoiceDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceDALDto> findByNumber(Long number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<InvoiceDALDto> findNotInvoicedWorkOrdersByDni(String dni) {
		List<InvoiceDALDto> workOrders = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(TINVOICES_findNotInvoicedWorkOrdersByDni);
			pst.setString(1, dni);
			rs = pst.executeQuery();
			
			workOrders = InvoiceAssembler.toInvoiceList(rs);
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
		return workOrders;
	}

}
