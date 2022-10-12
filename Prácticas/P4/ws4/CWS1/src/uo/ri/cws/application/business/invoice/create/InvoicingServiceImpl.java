/**
 * 
 */
package uo.ri.cws.application.business.invoice.create;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.create.commands.FindNotInvoicedWorkOrders;

/**
 * @author UO285176
 *
 */
public class InvoicingServiceImpl implements InvoicingService{

	@Override
	public InvoiceBLDto createInvoiceFor(List<String> workOrderIds) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderForInvoicingBLDto> findWorkOrdersByClientDni(String dni) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WorkOrderForInvoicingBLDto> findNotInvoicedWorkOrdersByClientDni(String dni) throws BusinessException {
		FindNotInvoicedWorkOrders fniwo = new FindNotInvoicedWorkOrders(dni);
		return fniwo.execute();
	}

	@Override
	public List<WorkOrderForInvoicingBLDto> findWorkOrdersByPlateNumber(String plate) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<InvoiceBLDto> findInvoiceByNumber(Long number) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentMeanForInvoicingBLDto> findPayMeansByClientDni(String dni) throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void settleInvoice(String invoiceId, List<Charge_BLDto> charges) throws BusinessException {
		// TODO Auto-generated method stub
		
	}

}
