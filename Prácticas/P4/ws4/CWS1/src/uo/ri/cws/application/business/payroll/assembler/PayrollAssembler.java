package uo.ri.cws.application.business.payroll.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

public class PayrollAssembler {

	public static Optional<PayrollBLDto> toBLDto(Optional<PayrollDALDto> arg) {
		Optional<PayrollBLDto> result = (arg.isEmpty() || arg == null)
				? Optional.ofNullable(null)
				: Optional.ofNullable(toPayrollBLDto(arg.get()));
		return result;
	}

	private static PayrollBLDto toPayrollBLDto(PayrollDALDto x) {
		PayrollBLDto payroll = new PayrollBLDto();
		payroll.id = x.id;
		payroll.bonus = x.bonus;
		payroll.date = x.date;
		payroll.incomeTax = x.incomeTax;
		payroll.monthlyWage = x.monthlyWage;
		payroll.nic = x.nic;
		payroll.productivityBonus = x.productivityBonus;
		payroll.trienniumPayment = x.trienniumPayment;
		payroll.version = x.version;
		payroll.contractId = x.contractId;
		payroll.netWage = calculateNetWage(x);
		return payroll;
	}

	private static double calculateNetWage(PayrollDALDto p) {
		// Abonos -> monthlyWage + bonus productivityBonus + trienniumpayment
		double salary = p.monthlyWage;
		double bonus = p.bonus;
		double pBonus = p.productivityBonus;
		double trienn = p.trienniumPayment;

		return salary + bonus + pBonus + trienn - p.incomeTax - p.nic;
	}

	public static List<PayrollSummaryBLDto> toBLDtoList(
			List<PayrollDALDto> arg) {
		List<PayrollSummaryBLDto> result = new ArrayList<PayrollSummaryBLDto>();
		for (PayrollDALDto mr : arg)
			result.add(toPayrollSummaryBLDto(mr));
		return result;
	}

	private static PayrollSummaryBLDto toPayrollSummaryBLDto(PayrollDALDto rs) {
		PayrollSummaryBLDto p = new PayrollSummaryBLDto();
		p.id = rs.id;
		p.version = rs.version;
		p.date = rs.date;
		p.netWage = calculateNetWage(rs);
		return p;
	}

	private static PayrollSummaryBLDto toPayrollSummaryBLDtoDALD(
			PayrollDALDto p2) {
		PayrollSummaryBLDto p = new PayrollSummaryBLDto();
		p.id = p2.id;
		p.version = p2.version;
		p.date = p2.date;
		p.netWage = calculateNetWage(p2);
		return p;
	}

	public static PayrollSummaryBLDto toSummaryBLDto(PayrollDALDto p) {
		return toPayrollSummaryBLDtoDALD(p);
	}

}
