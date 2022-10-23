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
	public void update(WorkOrderDALDto workOrder) {
		PreparedStatement pst = null;
		Connection c = null;
		try {
			c = Jdbc.getCurrentConnection();
			//pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_update"));
			pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_updateInvoice"));

//			pst.setDouble(1, workOrder.amount);
//			pst.setDate(2, Date.valueOf(workOrder.date.toLocalDate()));
//			pst.setString(3, workOrder.description);
//			pst.setString(4, workOrder.state);
//			pst.setLong(5, workOrder.version);
//			pst.setString(6, workOrder.invoice_id);
//			pst.setString(7, workOrder.mechanic_id);
//			pst.setString(8, workOrder.vehicle_id);
//			pst.setString(9, workOrder.id);
			
			pst.setString(1, workOrder.invoice_id);
			pst.setString(2, workOrder.id);
			
			pst.executeQuery();
		} catch (SQLException e) {
			throw new PersistenceException("Database error");
		} finally {
			if (pst != null)
				try {
					pst.close();
				} catch (SQLException e) {
					/* ignore */ }
		}
	}

	@Override
	public Optional<WorkOrderDALDto> findById(String id) {
		Optional<WorkOrderDALDto> workOrder = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Connection c = null;
		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_findByIds"));

			pst.setString(1, id);

			rs = pst.executeQuery();

			workOrder = WorkOrderAssembler.toWorkOrderDALDto(rs);
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
		}
		return workOrder;
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
	public List<WorkOrderDALDto> findByIds(List<String> workOrderIDS) {
		List<WorkOrderDALDto> workorders = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TWORKORDERS_findByIds"));

			for (String id : workOrderIDS) {
				pst.setString(1, id);
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
		}
		return workorders;
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
