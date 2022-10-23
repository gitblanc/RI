/**
 * 
 */
package uo.ri.cws.application.business.invoice.create.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.invoice.assembler.InvoicingAssembler;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.ClientGateway.ClientDALDto;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleDALDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

/**
 * @author UO285176
 *
 */
public class FindNotInvoicedWorkOrders implements Command<List<WorkOrderForInvoicingBLDto>> {
	String dni = null;

	public FindNotInvoicedWorkOrders(String dni) {
		Argument.isNotNull(dni, "The dni can't be null");
		Argument.isNotEmpty(dni, "The dni can't be empty");
		this.dni = dni;
	}

	/**
	 * ORIGINAL QUERY
	 * "select a.id, a.description, a.date, a.state, a.amount " + "from TWorkOrders
	 * as a, TVehicles as v, TClients as c " + "where a.vehicle_id = v.id " + " and
	 * v.client_id = c.id " + "and state <> 'INVOICED'" + " and dni like ?";
	 */
	public List<WorkOrderForInvoicingBLDto> execute() throws BusinessException {
		ClientGateway cg = PersistenceFactory.forClient();

		// Comprobación de si el cliente existe
		Optional<ClientDALDto> client = cg.findByDni(dni);
		BusinessCheck.isTrue(!client.isEmpty(), "The client of the invoice doesn't exist");

		List<VehicleDALDto> vehicles = obtainVehicles(client.get());
		List<WorkOrderDALDto> workOrders = obtainWorkOrders(vehicles);
		List<WorkOrderForInvoicingBLDto> notInvoicedWorkOrders = obtainNotInvoicedWorkOrders(workOrders);// workorders
																											// filtrados
																											// por el
																											// estado

		return notInvoicedWorkOrders;
	}

	private List<WorkOrderForInvoicingBLDto> obtainNotInvoicedWorkOrders(List<WorkOrderDALDto> workOrders) {

		return InvoicingAssembler.toInvoicingWorkOrderList(workOrders);
	}

	private List<WorkOrderDALDto> obtainWorkOrders(List<VehicleDALDto> vehicles) {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		List<String> vehiclesIds = new ArrayList<>();
		for (VehicleDALDto v : vehicles) {
			vehiclesIds.add(v.id);
		}
		return wg.findNotInvoicedForVehicles(vehiclesIds);
	}

	private List<VehicleDALDto> obtainVehicles(ClientDALDto client) throws BusinessException {
		VehicleGateway vg = PersistenceFactory.forVehicle();
		return vg.findByClient(client.id);
	}
}
