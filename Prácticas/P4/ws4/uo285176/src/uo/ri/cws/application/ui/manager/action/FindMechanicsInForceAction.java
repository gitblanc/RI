/**
 * 
 */
package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.ui.util.Printer;

/**
 * @author UO285176
 *
 */
public class FindMechanicsInForceAction implements Action {
	@Override
	public void execute() throws Exception {
		MechanicService ms = BusinessFactory.forMechanicService();
		// Print result
		Console.println("Mechanics in force:");
		Printer.printMechanics(ms.findMechanicsInForce());
	}

}
