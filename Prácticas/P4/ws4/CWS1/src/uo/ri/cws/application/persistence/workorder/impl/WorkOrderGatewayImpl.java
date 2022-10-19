/**
 * 
 */
package uo.ri.cws.application.persistence.workorder.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.util.Conf;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.assembler.WorkOrderAssembler;

/**
 * @author UO285176
 *
 */
public class WorkOrderGatewayImpl implements WorkOrderGateway {

	@Override
	public void add(WorkOrderDALDto t) {

	}

	@Override
	public void remove(String id) {

	}

	@Override
	public void update(WorkOrderDALDto t) {

	}

	@Override
	public Optional<WorkOrderDALDto> findById(String id) {
		return null;
	}

	@Override
	public List<WorkOrderDALDto> findAll() {

		return null;
	}

	@Override
	public List<WorkOrderDALDto> findByMechanic(String id) {

		return null;
	}

	@Override
	public List<WorkOrderDALDto> findNotInvoicedForVehicles(List<String> vehicleIds) {
		List<WorkOrderDALDto> workorders = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_findNotInvoicedVehicles"));

			for (String v : vehicleIds) {
				pst.setString(1, v);
				rs = pst.executeQuery();
			}

			workorders = WorkOrderAssembler.toWorkOrderDALDtoList(rs);

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
		return workorders;
	}

	@Override
	public List<WorkOrderDALDto> findByVehicleId(String vehicleId) {

		return null;
	}

	@Override
	public List<WorkOrderDALDto> findByIds(List<String> arg) {

		return null;
	}

	@Override
	public List<WorkOrderDALDto> findByInvoice(String id) {

		return null;
	}

	@Override
	public List<WorkOrderDALDto> findInvoiced() {

		return null;
	}

}
