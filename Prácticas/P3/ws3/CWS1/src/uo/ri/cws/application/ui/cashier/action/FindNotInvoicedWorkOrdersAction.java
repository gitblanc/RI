package uo.ri.cws.application.ui.cashier.action;

import console.Console;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.BusinessFactory;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.InvoicingService.WorkOrderForInvoicingBLDto;

public class FindNotInvoicedWorkOrdersAction implements Action {

	@Override
	public void execute() throws BusinessException {
		WorkOrderForInvoicingBLDto workOrder = new WorkOrderForInvoicingBLDto();
		workOrder.id = Console.readString("Client DNI ");
		InvoicingService ms = BusinessFactory.forInvoicingService();
		ms.findNotInvoicedWorkOrdersByClientDni(workOrder.id);
		Console.println("\nClient's not invoiced work orders\n");  
	}

}