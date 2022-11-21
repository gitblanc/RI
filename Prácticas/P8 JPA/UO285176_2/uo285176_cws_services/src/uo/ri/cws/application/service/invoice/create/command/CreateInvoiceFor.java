package uo.ri.cws.application.service.invoice.create.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.BusinessChecks;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.util.assertion.ArgumentChecks;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private List<String> workOrderIds;
    private WorkOrderRepository wrepo = Factory.repository.forWorkOrder();
    private InvoiceRepository irepo = Factory.repository.forInvoice();
    private List<WorkOrder> workOrders = new ArrayList<>();

    public CreateInvoiceFor(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds);
	ArgumentChecks.isTrue(!workOrderIds.isEmpty());
	for (String s : workOrderIds) {
	    ArgumentChecks.isNotNull(s,
		    "The list of workOrdersIds can't contain null elements");
	    ArgumentChecks.isNotEmpty(s,
		    "The list of workOrdersIds can't contain empty elements");
	    ArgumentChecks.isNotBlank(s,
		    "The list of workOrdersIds can't contain blank elements");
	}
	this.workOrderIds = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
	BusinessChecks.isTrue(checkWorkOrdersExist());
	BusinessChecks.isTrue(checkWorkOrdersFinished());
	Invoice i = createNewInvoice();
	irepo.add(i);
	return DtoAssembler.toDto(i);
    }

    private boolean checkWorkOrdersFinished() {
	List<WorkOrder> works = new ArrayList<>();
	for (String id : workOrderIds) {
	    WorkOrder w = wrepo.findById(id).get();
	    if (!w.getStatus().equals(WorkOrderState.FINISHED)) {
		return false;
	    }
	    works.add(w);
	}
	workOrders = works;
	return true;
    }

    private boolean checkWorkOrdersExist() {
	for (String id : workOrderIds) {
	    if (wrepo.findById(id).isEmpty())
		return false;
	}
	return true;
    }

    private Invoice createNewInvoice() {
	long numberInvoice = generateInvoiceNumber();
	LocalDate dateInvoice = LocalDate.now();

	// los workOrders se linkan y se marcan como INVOICED automáticamente
	Invoice i = new Invoice(numberInvoice, dateInvoice, this.workOrders);

	return i;
    }

    private long generateInvoiceNumber() {
	if (irepo.getNextInvoiceNumber() == null)
	    return 0;
	return irepo.getNextInvoiceNumber();
    }
}
