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
import uo.ri.cws.application.persistence.util.Conf;

/**
 * @author UO285176
 *
 */
public class ClientGatewayImpl implements ClientGateway {

	@Override
	public void add(ClientDALDto t) {

	}

	@Override
	public void remove(String id) {

	}

	@Override
	public void update(ClientDALDto t) {

	}

	@Override
	public Optional<ClientDALDto> findById(String id) {
		Optional<ClientDALDto> client = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TCLIENTS_findById"));
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

			pst = c.prepareStatement(Conf.getInstance().getProperty("TCLIENTS_findByDni"));
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
