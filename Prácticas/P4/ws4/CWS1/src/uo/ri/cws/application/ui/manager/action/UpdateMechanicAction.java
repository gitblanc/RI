package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;

public class UpdateMechanicAction implements Action {
	@Override
	public void execute() throws BusinessException {
		MechanicBLDto mechanic = new MechanicBLDto();
		// Get info
		mechanic.id = Console.readString("Type mechanic id to update");
		mechanic.name = Console.readString("Name");
		mechanic.surname = Console.readString("Surname");
		mechanic.dni = Console.readString("Dni");
		MechanicService ms = BusinessFactory.forMechanicService();
		ms.updateMechanic(mechanic);
		// Print result
		Console.println("Mechanic updated");
	}

}
