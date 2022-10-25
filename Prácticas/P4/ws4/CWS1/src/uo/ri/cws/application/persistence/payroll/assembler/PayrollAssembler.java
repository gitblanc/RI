/**
 * 
 */
package uo.ri.cws.application.persistence.payroll.assembler;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollSummaryDALDto;

/**
 * @author UO285176
 *
 */
public class PayrollAssembler {

	public static Optional<PayrollDALDto> toPayrollDALDto(ResultSet rs)
			throws SQLException {
		if (rs.next()) {
			return Optional.of(resultSetToPayrollDALDto(rs));
		} else
			return Optional.ofNullable(null);
	}

	private static PayrollDALDto resultSetToPayrollDALDto(ResultSet rs)
			throws SQLException {
		PayrollDALDto payroll = new PayrollDALDto();
		payroll.id = rs.getString("id");
		payroll.bonus = rs.getDouble("bonus");
		payroll.date = rs.getDate("date").toLocalDate();
		payroll.incomeTax = rs.getDouble("incometax");
		payroll.monthlyWage = rs.getDouble("monthlywage");
		payroll.nic = rs.getDouble("nic");
		payroll.productivityBonus = rs.getDouble("productivitybonus");
		payroll.trienniumPayment = rs.getDouble("trienniumPayment");
		payroll.version = rs.getLong("version");
		payroll.contractId = rs.getString("contract_Id");
		// payroll.netWage = NULL;
		return payroll;
	}

	public static List<PayrollDALDto> toPayrollDALDtoList(ResultSet rs)
			throws SQLException {
		List<PayrollDALDto> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToPayrollDALDto(rs));
		}

		return res;
	}

	public static List<PayrollSummaryDALDto> toPayrollDALDtoListSummary(
			ResultSet rs) throws SQLException {
		List<PayrollSummaryDALDto> res = new ArrayList<>();
		while (rs.next()) {
			res.add(resultSetToPayrollSummaryDALDto(rs));
		}

		return res;
	}

	private static PayrollSummaryDALDto resultSetToPayrollSummaryDALDto(
			ResultSet rs) throws SQLException {
		PayrollSummaryDALDto p = new PayrollSummaryDALDto();
		p.id = rs.getString("id");
		p.version = rs.getLong("version");
		p.date = rs.getDate("date").toLocalDate();
		p.netWage = 0;
		return p;
	}

}
