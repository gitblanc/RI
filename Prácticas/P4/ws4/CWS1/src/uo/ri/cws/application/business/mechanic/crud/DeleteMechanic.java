/**
 * 
 */
package uo.ri.cws.application.business.mechanic.crud;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;

/**
 * @author UO285176
 *
 */
public class DeleteMechanic implements Command<MechanicBLDto> {

	private MechanicBLDto mechanic = new MechanicBLDto();

	public DeleteMechanic(String idMechanic) {
		Argument.isNotNull(idMechanic, "The mechanic id can't be null");
		Argument.isNotEmpty(idMechanic, "The mechanic id can't be empty");
		this.mechanic.id = idMechanic;
	}

	@Override
	public MechanicBLDto execute() throws BusinessException {
		MechanicGateway mg = PersistenceFactory.forMechanic();
		ContractGateway cg = PersistenceFactory.forContract();
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		// Comprobaci�n de que el mec�nico no exista
		BusinessCheck.isTrue(!mg.findById(mechanic.id).isEmpty(),
				"The mechanic doesn't exist");
		// Comprobaci�n de que no hay ning�n work order para este mec�nico
		BusinessCheck.isTrue(wg.findByMechanic(mechanic.id).isEmpty(),
				"The mechanic still have some work orders");
		// Comprobaci�n de que el mec�nico no tiene ning�n contrato
		BusinessCheck.isTrue(cg.findContractById(mechanic.id).isEmpty(),
				"The mechanic still have some contracts");
		mg.remove(mechanic.id);
		return null;
	}
}
