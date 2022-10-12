package uo.ri.cws.application.persistence.mechanic.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.assembler.MechanicAssembler;

public class MechanicGatewayImpl implements MechanicGateway {

	private static String TMECHANICS_add = "insert into TMechanics(id, dni, name, surname, version) values (?, ?, ?, ?, ?)";
	private static String TMECHANICS_findByDni = "select * from tmechanics where dni = ?";
	private static final String TMECHANICS_findAll = "select id, dni, name, surname, version from TMechanics";

	@Override
	public void add(MechanicDALDto mechanic) {
		// Process
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(TMECHANICS_add);
			pst.setString(1, mechanic.id);
			pst.setString(2, mechanic.dni);
			pst.setString(3, mechanic.name);
			pst.setString(4, mechanic.surname);
			pst.setLong(5, mechanic.version);

			pst.executeUpdate();

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
	}

	@Override
	public void remove(String id) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(MechanicDALDto t) {
		// TODO Auto-generated method stub

	}

	@Override
	public Optional<MechanicDALDto> findById(String id) {
		return null;
	}

	@Override
	public List<MechanicDALDto> findAll() {
		List<MechanicDALDto> mechanics = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(TMECHANICS_findAll);

			rs = pst.executeQuery();
			mechanics = MechanicAssembler.toMechanicDALDtoList(rs);
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
		return mechanics;
	}

	@Override
	public Optional<MechanicDALDto> findByDni(String dni) {

		Optional<MechanicDALDto> mechanic = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(TMECHANICS_findByDni);
			pst.setString(1, dni);
			rs = pst.executeQuery();

			mechanic = MechanicAssembler.toMechanicDALDto(rs);// Fijarse en que sea el Assembler de persistence y no de
																// business

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

		}
		return mechanic;
	}

}
