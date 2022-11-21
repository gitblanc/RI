/**
 * 
 */
package uo.ri.ui.manager.mechanic.action;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.mechanic.MechanicCrudService;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.ui.util.Printer;
import uo.ri.util.console.Console;
import uo.ri.util.menu.Action;

/**
 * @author UO285176
 *
 */
public class FindMechanicsInForceAction implements Action {
    @Override
    public void execute() throws BusinessException {
	MechanicCrudService as = Factory.service.forMechanicCrudService();
	List<MechanicDto> mechanics = as.findMechanicsInForce();
	// Print result
	Console.println("\nMechanics in force\n");
	mechanics.forEach(m -> Printer.printMechanic(m));
    }

}
