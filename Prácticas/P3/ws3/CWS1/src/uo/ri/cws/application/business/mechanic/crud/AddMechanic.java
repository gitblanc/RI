/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

import assertion.Argument;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;

/**
 * @author UO285176
 *
 */
public class AddMechanic {

	private static String SQL = "insert into TMechanics(id, dni, name, surname, version) values (?, ?, ?, ?, ?)";
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	private MechanicBLDto mechanic = null;

	public AddMechanic(MechanicBLDto mechanic) {
		Argument.isNotNull(mechanic);
		this.mechanic = mechanic;
		this.mechanic.id = UUID.randomUUID().toString();
		this.mechanic.version = 1L;
	}

	// L�gica de negocio para a�adir a un mec�nico
	public MechanicBLDto execute() {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = DriverManager.getConnection(URL, USER, PASSWORD);

			pst = c.prepareStatement(SQL);
			pst.setString(1, mechanic.id);
			pst.setString(2, mechanic.dni);
			pst.setString(3, mechanic.name);
			pst.setString(4, mechanic.surname);
			pst.setLong(5, mechanic.version);

			pst.executeUpdate();

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
		return mechanic;
	}

}
