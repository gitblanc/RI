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
public class FindMechanicByIdAction implements Action {
	@Override
	public void execute() throws Exception {
		// Get info
		String id = Console.readString("Id mechanic");
		MechanicService ms = BusinessFactory.forMechanicService();
		// Print result
		Console.println("Mechanic:");
		Printer.printMechanic(ms.findMechanicById(id).get());
	}

}
