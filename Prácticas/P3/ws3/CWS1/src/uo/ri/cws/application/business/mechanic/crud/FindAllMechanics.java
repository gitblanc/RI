/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;

/**
 * @author UO285176
 *
 */
public class FindAllMechanics {
	
	private static final String SQL = "select id, dni, name, surname, version from TMechanics";
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	
	public List<MechanicBLDto> execute() {
		List<MechanicBLDto> mechanics = new ArrayList<>();
		
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);
			
			pst = c.prepareStatement(SQL);
			
			rs = pst.executeQuery();
			while(rs.next()) {
				MechanicBLDto mechanic = new MechanicBLDto();
				mechanic.id = rs.getString("id");
				mechanic.dni = rs.getString("dni");
				mechanic.name = rs.getString("name");
				mechanic.surname = rs.getString("surname");
				mechanic.version = rs.getLong("version");
				mechanics.add(mechanic);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
			if (c != null) try { c.close(); } catch(SQLException e) { /* ignore */ }
		}
		return mechanics;
	}
}
