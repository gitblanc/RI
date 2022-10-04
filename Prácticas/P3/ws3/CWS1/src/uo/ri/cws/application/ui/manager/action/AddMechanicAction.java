package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class AddMechanicAction implements Action {

	@Override
	public void execute() throws BusinessException {
		MechanicBLDto mechanic = new MechanicBLDto();
		// Get info
		mechanic.dni = Console.readString("Dni");
		mechanic.name = Console.readString("Name");
		mechanic.surname = Console.readString("Surname");

		MechanicService ms = BusinessFactory.forMechanicService();
		mechanic = ms.addMechanic(mechanic);
		// Print result
		Console.println("Mechanic added");
		Printer.printMechanic(mechanic);
	}

}
