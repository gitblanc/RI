/**
 * 
 */
package uo.ri.cws.application.persistence.client.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.assembler.ClientAssembler;

/**
 * @author UO285176
 *
 */
public class ClientGatewayImpl implements ClientGateway {
	
	public static final String TCLIENTS_findByDni = "select * from tclients where dni = ?";
	public static final String TCLIENTS_findById = "select * from tclients where id = ?";
	
	@Override
	public void add(ClientDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(ClientDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<ClientDALDto> findById(String id) {
		Optional<ClientDALDto> client = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(TCLIENTS_findById);
			pst.setString(1, id);
			rs = pst.executeQuery();
			
			client = ClientAssembler.toClientDALDto(rs);
		} catch (SQLException e) {
			throw new PersistenceException("Database error");// Esto hay que hacerlo en todos los errores de
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
		} // En los tdg NO SE CIERRAN LAS CONEXIONES
		return client;
	}

	@Override
	public List<ClientDALDto> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<ClientDALDto> findByDni(String dni) {
		Optional<ClientDALDto> client = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(TCLIENTS_findByDni);
			pst.setString(1, dni);
			rs = pst.executeQuery();

			client = ClientAssembler.toClientDALDto(rs);

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
		return client;
	}

}
