package uo.ri.cws.application.ui.cashier.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import console.Console;
import menu.Action;
import uo.ri.cws.application.BusinessException;

public class FindNotInvoicedWorkOrdersAction implements Action {
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASS = "";


	/**
	 * Process:
	 * 
	 *   - Ask customer dni
	 *    
	 *   - Display all uncharged workorder  
	 *   		(state <> 'INVOICED'). For each workorder, display 
	 *   		id, vehicle id, date, state, amount and description
	 */

	private static final String SQL =
		"select a.id, a.description, a.date, a.state, a.amount " +
		"from TWorkOrders as a, TVehicles as v, TClients as c " +
		"where a.vehicle_id = v.id " +
		"	and v.client_id = c.id " +
		"	and state <> 'INVOICED'" +
		"	and dni like ?";

	@Override
	public void execute() throws BusinessException {
		String dni = Console.readString("Client DNI ");
		
		Console.println("\nClient's not invoiced work orders\n");  

		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = DriverManager.getConnection(URL, USER, PASS);
			
			pst = c.prepareStatement(SQL);
			pst.setString(1, dni);
			
			rs = pst.executeQuery();
			while(rs.next()) {
				Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n",  
					rs.getString(1)
					, rs.getString(2) 
					, rs.getDate(3)
					, rs.getString(4)
					, rs.getDouble(5)
				);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

}