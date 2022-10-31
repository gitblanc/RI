/**
 * 
 */
package uo.ri.cws.application.ui.manager;

import menu.BaseMenu;
import uo.ri.cws.application.ui.manager.action.DetailPayrollAction;
import uo.ri.cws.application.ui.manager.action.ListAllMechanicPayrollsAction;
import uo.ri.cws.application.ui.manager.action.ListAllPayrollsAction;
import uo.ri.cws.application.ui.manager.action.ListAllPayrollsByGroupAction;

/**
 * @author UO285176
 *
 */
public class PayrollMenu extends BaseMenu {

	public PayrollMenu() {
		menuOptions = new Object[][] {
				{ "Payrolls > Payrolls management", null },

				{ "List all payrolls", ListAllPayrollsAction.class },
				{ "Detail a payroll", DetailPayrollAction.class },
				{ "List all mechanic payrolls",
						ListAllMechanicPayrollsAction.class },
				{ "List all mechanics payrolls of a professional group",
						ListAllPayrollsByGroupAction.class } };
	}
}
