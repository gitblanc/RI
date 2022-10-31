/**
 * 
 */
package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.payroll.PayrollService;
import uo.ri.cws.application.ui.util.Printer;

/**
 * @author UO285176
 *
 */
public class DetailPayrollAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("\nDetail a payroll\n");
		String id = Console.readString("Introduce the payroll id");
		PayrollService ps = BusinessFactory.forPayrollService();
		Printer.printPayrolls(ps.getPayrollDetails(id).get());
	}

}
