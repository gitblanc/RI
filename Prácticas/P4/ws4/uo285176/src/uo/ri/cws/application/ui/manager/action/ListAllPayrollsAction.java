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
public class ListAllPayrollsAction implements Action{

	@Override
	public void execute() throws Exception {
		Console.println("\nList of payrolls \n");
		PayrollService ps = BusinessFactory.forPayrollService();
		Printer.printPayrollsSummary(ps.getAllPayrolls());
	}

}
