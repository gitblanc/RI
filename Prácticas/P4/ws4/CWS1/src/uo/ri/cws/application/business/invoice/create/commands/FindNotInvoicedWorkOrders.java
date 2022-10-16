/**
 * 
 */
package uo.ri.cws.application.business.invoice.create.commands;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.business.invoice.assembler.InvoicingAssembler;
import uo.ri.cws.application.business.mechanic.MechanicService.MechanicBLDto;
import uo.ri.cws.application.business.util.BusinessCheck;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.assembler.InvoiceAssembler;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway.VehicleDALDto;
import uo.ri.cws.application.persistence.vehicle.assembler.VehicleAssembler;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;
import uo.ri.cws.application.persistence.workorder.assembler.WorkOrderAssembler;

/**
 * @author UO285176
 *
 */
public class FindNotInvoicedWorkOrders implements Command<List<WorkOrderForInvoicingBLDto>>{
	
	//WorkOrderForInvoicingBLDto workOrder = new WorkOrderForInvoicingBLDto();
	String dni = null;
	
	public FindNotInvoicedWorkOrders(String dni) {
		this.dni = dni;
	}

	public List<WorkOrderForInvoicingBLDto> execute() throws BusinessException {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		//Comprobación de si el cliente existe
		BusinessCheck.isTrue(!wg.findById(dni).isEmpty(), "The client of the invoice doesn't exist");
		
		List<VehicleDALDto> vehicles = obtainVehicles();//FALTA
		List<String> vehiclesIds = obtainVehiclesIds(vehicles);//FALTA
		
		List<WorkOrderDALDto> workOrders = obtainNotInvoicedWorkOrders(vehiclesIds);//FALTA lo del state tambien
		
		List<WorkOrderForInvoicingBLDto> notInvoicedWorkOrders = InvoicingAssembler.toInvoicingWorkOrderList(workOrders);
		
		return notInvoicedWorkOrders;
	}

	private List<String> obtainVehiclesIds(List<VehicleDALDto> vehicles) {
		// TODO Auto-generated method stub
		return null;
	}

	private List<WorkOrderDALDto> obtainNotInvoicedWorkOrders(List<String> vehicles) {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		wg.findNotInvoicedForVehicles(vehicles);
	}

	private List<VehicleDALDto> obtainVehicles() {
		VehicleGateway vg = PersistenceFactory.forVehicle();
		List<VehicleDALDto> vehicles =  vg.findByClient(dni);//FALTA IMPLEMENTAR
		return vehicles;
	}
}
