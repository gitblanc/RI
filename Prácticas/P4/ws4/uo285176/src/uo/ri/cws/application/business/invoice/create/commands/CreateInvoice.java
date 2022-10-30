/**
 * 
 */
package uo.ri.cws.application.business.invoice.create.commands;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import assertion.Argument;
import math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto;
import uo.ri.cws.application.business.invoice.InvoicingService.InvoiceBLDto.InvoiceState;
import uo.ri.cws.application.business.invoice.assembler.InvoicingAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway.InvoiceDALDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

/**
 * @author UO285176
 *
 */
public class CreateInvoice implements Command<InvoiceBLDto> {

	private List<String> workOrderIds = new ArrayList<String>();

	public CreateInvoice(List<String> workOrderIds) {
		Argument.isNotNull(workOrderIds, "The workOrders can't be null");
		Argument.isTrue(!workOrderIds.isEmpty(),
				"The workOrders can't be empty");
		for (String s : workOrderIds) {
			Argument.isNotNull(s,
					"The list of workOrdersIds can't contain null elements");
			Argument.isNotEmpty(s,
					"The list of workOrdersIds can't contain empty elements");
		}
		this.workOrderIds = workOrderIds;
	}

	public InvoiceBLDto execute() throws BusinessException {
		InvoiceDALDto invoice = new InvoiceDALDto();

		if (!checkWorkOrdersExist(workOrderIds))
			throw new BusinessException("Workorder does not exist");
		if (!checkWorkOrdersFinished(workOrderIds))
			throw new BusinessException("Workorder is not finished yet");

		long numberInvoice = generateInvoiceNumber();
		LocalDate dateInvoice = LocalDate.now();
		double amount = calculateTotalInvoice(workOrderIds); // vat not included
		double vat = vatPercentage(amount, dateInvoice);
		double total = amount * (1 + vat / 100); // vat included
		total = Round.twoCents(total);

		invoice.id = UUID.randomUUID().toString();
		invoice.version = 1L;
		invoice.date = dateInvoice;
		invoice.number = numberInvoice;
		invoice.state = InvoiceState.NOT_YET_PAID.toString();
		invoice.amount = total;
		invoice.vat = vat;

		String idInvoice = createInvoice(invoice);
		linkWorkordersToInvoice(idInvoice, workOrderIds);
		markWorkOrderAsInvoiced(workOrderIds);
		updateVersion(workOrderIds);

		return InvoicingAssembler.toDto(invoice);
	}

	private void updateVersion(List<String> workOrderIds) {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		WorkOrderDALDto workOrder = null;
		for (String workOrderId : workOrderIds) {
			workOrder = wg.findById(workOrderId).get();
			workOrder.version = workOrder.version + 1;
		}
	}

	/*
	 * checks whether every work order exist
	 */
	private boolean checkWorkOrdersExist(List<String> workOrderIDS)
			throws BusinessException {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		for (String id : workOrderIDS) {
			if (wg.findById(id) == null || wg.findById(id).isEmpty())
				return false;
		}
		return true;
	}

	/*
	 * checks whether every work order id is FINISHED
	 */
	private boolean checkWorkOrdersFinished(List<String> workOrderIDS)
			throws BusinessException {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		for (String id : workOrderIDS) {
			if (!wg.findById(id).get().state.equals("FINISHED")) {
				return false;
			}
		}
		return true;
	}

	/*
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private Long generateInvoiceNumber() {
		InvoiceGateway ig = PersistenceFactory.forInvoice();
		return ig.getNextInvoiceNumber();
	}

	/*
	 * Compute total amount of the invoice (as the total of individual work
	 * orders' amount
	 */
	private double calculateTotalInvoice(List<String> workOrderIDS)
			throws BusinessException {

		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
			totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
	}

	/*
	 * checks whether every work order id is FINISHED
	 */
	private Double getWorkOrderTotal(String workOrderID)
			throws BusinessException {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		return wg.findById(workOrderID).get().amount;
	}

	/*
	 * returns vat percentage
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0
				: 18.0;

	}

	/*
	 * Creates the invoice in the database; returns the id
	 */
	private String createInvoice(InvoiceDALDto invoice) {
		InvoiceGateway ig = PersistenceFactory.forInvoice();
		ig.add(invoice);
		return invoice.id;
	}

	/*
	 * Set the invoice number field in work order table to the invoice number
	 * generated
	 */
	private void linkWorkordersToInvoice(String invoiceId,
			List<String> workOrderIDS) {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		List<WorkOrderDALDto> workOrders = wg.findByIds(workOrderIDS);
		for (WorkOrderDALDto workOrder : workOrders) {
			if (!wg.findById(workOrder.id).isEmpty()) {
				workOrder.invoice_id = invoiceId;
				wg.update(workOrder);
			}
		}
	}

	/*
	 * Sets state to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<String> ids) {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		WorkOrderDALDto workOrder = null;
		for (String id : ids) {
			workOrder = wg.findById(id).get();
			workOrder.state = "INVOICED";
			wg.update(workOrder);
		}
	}
}
