package uo.ri.cws.application.business;

import exception.NotYetImplementedException;
import uo.ri.cws.application.business.client.ClientService;
import uo.ri.cws.application.business.contract.ContractService;
import uo.ri.cws.application.business.contracttype.ContractTypeService;
import uo.ri.cws.application.business.invoice.InvoicingService;
import uo.ri.cws.application.business.invoice.create.InvoicingServiceImpl;
import uo.ri.cws.application.business.mechanic.MechanicService;
import uo.ri.cws.application.business.mechanic.crud.MechanicServiceImpl;
import uo.ri.cws.application.business.payroll.PayrollService;
import uo.ri.cws.application.business.professionalgroup.ProfessionalGroupService;

public class BusinessFactory {


	public static MechanicService forMechanicService() {
		return new MechanicServiceImpl();
	}

	public static InvoicingService forInvoicingService() {
		return new InvoicingServiceImpl();
	}

	public static ContractService forContractService() {
		
		throw new NotYetImplementedException();

	}

	public static PayrollService forPayrollService() {
		throw new NotYetImplementedException();

	}

	public static ClientService forClientService() {

		throw new NotYetImplementedException();

	}

	public static ContractTypeService forContractTypeService() {
		throw new NotYetImplementedException();

	}

	public static ProfessionalGroupService forProfessionalGroupService() {

		throw new NotYetImplementedException();

	}

}

