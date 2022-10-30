package uo.ri.cws.application.ui.manager.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;

public class DeleteMechanicAction implements Action {
	
	@Override
	public void execute() throws BusinessException {
		MechanicBLDto mechanic = new MechanicBLDto();
		mechanic.id = Console.readString("Type mechanic id "); 
		MechanicService ms = BusinessFactory.forMechanicService();
		ms.deleteMechanic(mechanic.id);
		Console.println("Mechanic deleted");
	}

}
