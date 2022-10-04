package uo.ri.cws.application.ui.manager.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import console.Console;
import menu.Action;
import uo.ri.cws.application.BusinessException;

public class UpdateMechanicAction implements Action {

	private static String SQL_UPDATE = 
		"update TMechanics " +
			"set name = ?, surname = ?, version = version+1 " +
			"where id = ?";
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	private Connection c = null;

	@Override
	public void execute() throws BusinessException {
		
		// Get info
		String id = Console.readString("Type mechahic id to update"); 
		String name = Console.readString("Name"); 
		String surname = Console.readString("Surname");
		
		// Process
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
			pst = c.prepareStatement(SQL_UPDATE);
			pst.setString(1, name);
			pst.setString(2, surname);
			pst.setString(3, id);
			
			pst.executeUpdate();
			
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		
		// Print result
		Console.println("Mechanic updated");
	}

}
