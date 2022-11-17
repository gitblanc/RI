package uo.ri.cws.application.service.invoice.create.command;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

public class CreateInvoiceFor implements Command<InvoiceDto> {

    private List<String> workOrderIds;
    private WorkOrderRepository wrkrsRepo = Factory.repository.forWorkOrder();
    private InvoiceRepository invsRepo = Factory.repository.forInvoice();
    private List<WorkOrder> workOrders = new ArrayList<>();

    public CreateInvoiceFor(List<String> workOrderIds) {
	ArgumentChecks.isNotNull(workOrderIds);
	for (String s : workOrderIds) {
	    ArgumentChecks.isNotNull(s,
		    "The list of workOrdersIds can't contain null elements");
	    ArgumentChecks.isNotEmpty(s,
		    "The list of workOrdersIds can't contain empty elements");
	    ArgumentChecks.isNotBlank(s,
		    "The list of workOrdersIds can't contain blank elements");
	    workOrders.add(wrkrsRepo.findById(s).get());
	}
	this.workOrderIds = workOrderIds;
    }

    @Override
    public InvoiceDto execute() throws BusinessException {
	Invoice i = createNewInvoice();
	invsRepo.add(i);
	return DtoAssembler.toDto(i);
    }

    private Invoice createNewInvoice() {
	long numberInvoice = generateInvoiceNumber();
	LocalDate dateInvoice = LocalDate.now();
	double amount = calculateTotalInvoice(); // vat not included
	double vat = vatPercentage(amount, dateInvoice);
	double total = amount * (1 + vat / 100); // vat included
	total = Round.twoCents(total);

	// los workOrders se linkan y se marcan como INVOICED automáticamente
	Invoice i = new Invoice(numberInvoice, dateInvoice, this.workOrders);

	return i;
    }

    private long generateInvoiceNumber() {
	return invsRepo.getNextInvoiceNumber();
    }

    private double calculateTotalInvoice() {
	double totalInvoice = 0.0;
	for (String workOrderID : workOrderIds) {
	    totalInvoice += getWorkOrderTotal(workOrderID);
	}
	return totalInvoice;
    }

    private double getWorkOrderTotal(String workOrderID) {
	return wrkrsRepo.findById(workOrderID).get().getAmount();
    }

    private double vatPercentage(double amount, LocalDate dateInvoice) {
	return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0
		: 18.0;
    }
}
