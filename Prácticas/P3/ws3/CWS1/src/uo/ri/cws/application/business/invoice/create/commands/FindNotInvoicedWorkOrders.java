/**
 * 
 */
package uo.ri.cws.application.business.invoice.create.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;

/**
 * @author UO285176
 *
 */
public class FindNotInvoicedWorkOrders {
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASS = "";
	WorkOrderForInvoicingBLDto workOrder = null;

	/**
	 * Process:
	 * 
	 * - Ask customer dni
	 * 
	 * - Display all uncharged workorder (state <> 'INVOICED'). For each workorder,
	 * display id, vehicle id, date, state, amount and description
	 */

	private static final String SQL = "select a.id, a.description, a.date, a.state, a.amount "
			+ "from TWorkOrders as a, TVehicles as v, TClients as c " + "where a.vehicle_id = v.id "
			+ "	and v.client_id = c.id " + "	and state <> 'INVOICED'" + "	and dni like ?";

	public FindNotInvoicedWorkOrders(String dni) {
		Argument.isNotNull(dni);
		workOrder.id = dni;
	}

	public List<WorkOrderForInvoicingBLDto> execute() {
		List<WorkOrderForInvoicingBLDto> workOrders = new ArrayList<>();
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = DriverManager.getConnection(URL, USER, PASS);

			pst = c.prepareStatement(SQL);
			pst.setString(1, workOrder.id);

			rs = pst.executeQuery();
			while (rs.next()) {
				WorkOrderForInvoicingBLDto worker = new WorkOrderForInvoicingBLDto();
				worker.id = rs.getString("id");
				worker.description = rs.getString("description");
				worker.state = rs.getString("state");
				worker.date = rs.getDate(3);
				worker.total = rs.getDouble("total");
				workOrders.add(worker);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
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
			if (c != null)
				try {
					c.close();
				} catch (SQLException e) {
					/* ignore */ }
		}
		return workOrders;
	}
}
