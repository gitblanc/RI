package uo.ri.cws.application.business.payroll.assembler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollSummaryDALDto;

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
		payroll.netWage = x.netWage;
		return payroll;
	}

	public static List<PayrollSummaryBLDto> toBLDtoList(
			List<PayrollSummaryDALDto> arg) {
		List<PayrollSummaryBLDto> result = new ArrayList<PayrollSummaryBLDto>();
		for (PayrollSummaryDALDto mr : arg)
			result.add(toPayrollSummaryBLDto(mr));
		return result;
	}

	private static PayrollSummaryBLDto toPayrollSummaryBLDto(
			PayrollSummaryDALDto rs) {
		PayrollSummaryBLDto p = new PayrollSummaryBLDto();
		p.id = rs.id;
		p.version = rs.version;
		p.date = rs.date;
		p.netWage = rs.netWage;
		return p;
	}

	private static PayrollSummaryBLDto toPayrollSummaryBLDtoDALD(
			PayrollDALDto p2) {
		PayrollSummaryBLDto p = new PayrollSummaryBLDto();
		p.id = p2.id;
		p.version = p2.version;
		p.date = p2.date;
		p.netWage = p2.netWage;
		return p;
	}

	public static PayrollSummaryBLDto toSummaryBLDto(PayrollDALDto p) {
		return toPayrollSummaryBLDtoDALD(p);
	}

}
