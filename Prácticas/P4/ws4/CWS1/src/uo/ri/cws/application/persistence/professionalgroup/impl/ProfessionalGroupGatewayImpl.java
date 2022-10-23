/**
 * 
 */
package uo.ri.cws.application.persistence.professionalgroup.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import jdbc.Jdbc;
import uo.ri.cws.application.persistence.PersistenceException;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.assembler.ProfessionalGroupAssembler;
import uo.ri.cws.application.persistence.util.Conf;

/**
 * @author UO285176
 *
 */
public class ProfessionalGroupGatewayImpl implements ProfessionalGroupGateway {

	@Override
	public void add(ProfessionalGroupDALDto group) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TPROFESSIONALGROUPS_add"));
			pst.setString(1, group.id);
			pst.setString(2, group.name);
			pst.setDouble(3, group.productivityRate);
			pst.setDouble(4, group.trieniumSalary);
			pst.setLong(5, group.version);

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
		}
	}

	@Override
	public void remove(String id) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TPROFESSIONALGROUPS_remove"));
			pst.setString(1, id);

			pst.executeUpdate();

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
	}

	@Override
	public void update(ProfessionalGroupDALDto group) {
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();
			pst = c.prepareStatement(Conf.getInstance().getProperty("TPROFESSIONALGROUPS_update"));
			pst.setString(1, group.name);
			pst.setDouble(2, group.productivityRate);
			pst.setDouble(3, group.trieniumSalary);
			pst.setLong(4, group.version);
			pst.setString(5, group.id);

			pst.executeUpdate();

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
	}

	@Override
	public Optional<ProfessionalGroupDALDto> findById(String id) {
		Optional<ProfessionalGroupDALDto> group = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TPROFESSIONALGROUPS_findById"));
			pst.setString(1, id);
			rs = pst.executeQuery();

			group = ProfessionalGroupAssembler.toProfessionalGroupDALDto(rs);// Fijarse en que sea el Assembler de
																				// persistence y no de
			// business

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
		return group;
	}

	@Override
	public List<ProfessionalGroupDALDto> findAll() {
		List<ProfessionalGroupDALDto> groups = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TPROFESSIONALGROUPS_findAll"));

			rs = pst.executeQuery();
			groups = ProfessionalGroupAssembler.toProfessionalGroupDALDtoList(rs);
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
		return groups;
	}

	@Override
	public Optional<ProfessionalGroupDALDto> findByName(String name) {
		Optional<ProfessionalGroupDALDto> group = null;
		Connection c = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			c = Jdbc.getCurrentConnection();

			pst = c.prepareStatement(Conf.getInstance().getProperty("TPROFESSIONALGROUPS_findByName"));
			pst.setString(1, name);
			rs = pst.executeQuery();

			group = ProfessionalGroupAssembler.toProfessionalGroupDALDto(rs);// Fijarse en que sea el Assembler de
																				// persistence y no de
			// business

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
		return group;
	}

}
