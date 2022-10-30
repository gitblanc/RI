/**
 * 
 */
package uo.ri.cws.application.persistence.payroll;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.persistence.Gateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;;

/**
 * @author UO285176
 *
 */
public interface PayrollGateway extends Gateway<PayrollDALDto> {

	public class PayrollDALDto {

		public String id;
		public long version;

		public String contractId;
		public LocalDate date;

		// Earnings
		public double monthlyWage;
		public double bonus;
		public double productivityBonus;
		public double trienniumPayment;

		// Deductions
		public double incomeTax;
		public double nic;

		// Net wage
		public double netWage;
	}

	public class PayrollSummaryDALDto {

		public String id;
		public long version;

		public LocalDate date;

		public double netWage;

	}

	public List<PayrollDALDto> findByContractId(String id);

	public List<PayrollSummaryDALDto> findAllSummary();
}
