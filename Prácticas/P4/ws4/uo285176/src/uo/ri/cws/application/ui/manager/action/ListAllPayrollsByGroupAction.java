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
public class ListAllPayrollsByGroupAction implements Action {

	@Override
	public void execute() throws Exception {
		Console.println("\nList payrolls of a professional group \n");
		String name = Console.readString("Introduce the group name");
		PayrollService ps = BusinessFactory.forPayrollService();
		Printer.printPayrollsSummary(
				ps.getAllPayrollsForProfessionalGroup(name));
	}

}
