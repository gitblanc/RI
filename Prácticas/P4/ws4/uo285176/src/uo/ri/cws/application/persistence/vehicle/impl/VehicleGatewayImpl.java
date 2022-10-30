/**
 * 
 */
package uo.ri.cws.application.persistence.vehicle.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.assembler.VehicleAssembler;

/**
 * @author UO285176
 *
 */
public class VehicleGatewayImpl implements VehicleGateway {

	@Override
	public void add(VehicleDALDto t) {

	}

	@Override
	public void remove(String id) {

	}

	@Override
	public void update(VehicleDALDto t) {

	}

	@Override
	public Optional<VehicleDALDto> findById(String id) {
		Optional<VehicleDALDto> vehicle = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TVEHICLES_findById"));
			pst.setString(1, id);
			rs = pst.executeQuery();

			vehicle = VehicleAssembler.toVehicleDALDto(rs);

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
		return vehicle;
	}

	@Override
	public List<VehicleDALDto> findAll() {

		return null;
	}

	@Override
	public List<VehicleDALDto> findByClient(String id) {
		List<VehicleDALDto> vehicles = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TVEHICLES_findByClientId"));
			pst.setString(1, id);
			rs = pst.executeQuery();

			vehicles = VehicleAssembler.toVehicleDALDtoList(rs);

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
		return vehicles;
	}

}
