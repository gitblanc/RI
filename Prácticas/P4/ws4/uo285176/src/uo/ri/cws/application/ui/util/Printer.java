package uo.ri.cws.application.ui.util;

import java.util.List;

import console.Console;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;

public class Printer {

	public static void printMechanic(MechanicBLDto m) {
		Console.printf("\t%-36.36s %-9s %-10.10s %-25.25s %-10.2s\n", m.id,
				m.dni, m.name, m.surname, m.version);
	}

	public static void printMechanics(List<MechanicBLDto> list) {

		Console.printf("\t%-36s %-9s %-10s %-25s %-10s\n",
				"Mechanic identifier", "DNI", "Name", "Surname", "Version");
		for (MechanicBLDto m : list)
			printMechanic(m);
	}

	public static void printInvoice(InvoiceBLDto invoice) {

		double importeConIVa = invoice.total;
		double iva = invoice.vat;
		double importeSinIva = importeConIVa / (1 + iva / 100);

		Console.printf("Invoice number: %d%n", invoice.number);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY%n", invoice.date);
		Console.printf("\tAmount: %.2f %n", importeSinIva);
		Console.printf("\tVat: %.1f %% %n", invoice.vat);
		Console.printf("\tTotal (vat included): %.2f %n", invoice.total);
		Console.printf("\tState: %s%n", invoice.state);
	}

	public static void printInvoicingWorkOrder(WorkOrderForInvoicingBLDto arg) {

		Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%.2f\n", arg.id,
				arg.description, arg.date, arg.state, arg.total);
	}

	public static void printInvoicingWorkOrders(
			List<WorkOrderForInvoicingBLDto> arg) {
		Console.printf("\t%s \t%-40.40s \t%s \t%-12.12s \t%s\n", "Identifier",
				"description", "date", "state", "total");
		for (WorkOrderForInvoicingBLDto inv : arg)
			printInvoicingWorkOrder(inv);
	}

	public static void printPayrollsSummary(
			List<PayrollSummaryBLDto> allPayrolls) {
		Console.printf("\t%s \t%s \t%s \t%s \t%s\n", "Identifier", "version",
				"date", "netWage");
		for (PayrollSummaryBLDto p : allPayrolls)
			printPayrollSummary(p);
	}

	private static void printPayrollSummary(PayrollSummaryBLDto p) {
		Console.printf("\t%s \t%s \t%s \t%s \t%.2f\n", p.id, p.version, p.date,
				p.netWage);
	}

	public static void printPayrolls(PayrollBLDto p) {
		Console.printf(
				"\t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s\n",
				"Identifier", "version", "contract id", "date", "monthly wage",
				"bonus", "produtivity bonus", "triennium payment", "income tax",
				"nic", "net wage");
		Console.printf(
				"\t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s \t%s\n",
				p.id, p.version, p.contractId, p.date, p.monthlyWage, p.bonus,
				p.productivityBonus, p.trienniumPayment, p.incomeTax, p.nic,
				p.netWage);

	}

}