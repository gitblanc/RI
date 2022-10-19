package uo.ri.cws.application.ui.cashier.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;
import uo.ri.cws.application.ui.util.Printer;

public class FindNotInvoicedWorkOrdersAction implements Action {

	@Override
	public void execute() throws BusinessException {
		WorkOrderForInvoicingBLDto workOrder = new WorkOrderForInvoicingBLDto();
		workOrder.id = Console.readString("Client DNI ");
		InvoicingService ms = BusinessFactory.forInvoicingService();
		Console.println("\nClient's not invoiced work orders\n");  
		Printer.printInvoicingWorkOrders(ms.findNotInvoicedWorkOrdersByClientDni(workOrder.id));
	}

}