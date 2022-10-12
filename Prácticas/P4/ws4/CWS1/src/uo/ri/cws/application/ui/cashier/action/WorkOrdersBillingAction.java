package uo.ri.cws.application.ui.cashier.action;

import java.util.List;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class WorkOrdersBillingAction implements Action {

	@Override
	public void execute() throws BusinessException {
		WorkOrderForInvoicingBLDto workOrder = new WorkOrderForInvoicingBLDto();
		workOrder.id = Console.readString("Type work order ids");
		InvoicingService ms = BusinessFactory.forInvoicingService();
		List<WorkOrderForInvoicingBLDto> lista = ms.findWorkOrdersByClientDni(workOrder.id);
		Console.println("\nWork orders billing\n");
		Printer.printInvoicingWorkOrders(lista);
	}

}
