package uo.ri.cws.application.persistence;

import uo.ri.cws.application.persistence.client.ClientGateway;
import uo.ri.cws.application.persistence.client.impl.ClientGatewayImpl;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.impl.ContractGatewayImpl;
import uo.ri.cws.application.persistence.contracttype.ContractTypeGateway;
import uo.ri.cws.application.persistence.contracttype.impl.ContractTypeGatewayImpl;
import uo.ri.cws.application.persistence.invoice.InvoiceGateway;
import uo.ri.cws.application.persistence.invoice.impl.InvoiceGatewayImpl;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.impl.MechanicGatewayImpl;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.impl.PayrollGatewayImpl;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway;
import uo.ri.cws.application.persistence.professionalgroup.impl.ProfessionalGroupGatewayImpl;
import uo.ri.cws.application.persistence.vehicle.VehicleGateway;
import uo.ri.cws.application.persistence.vehicle.impl.VehicleGatewayImpl;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.impl.WorkOrderGatewayImpl;

public class PersistenceFactory {

	public static MechanicGateway forMechanic() {
		return new MechanicGatewayImpl();
	}

	public static WorkOrderGateway forWorkOrder() {
		return new WorkOrderGatewayImpl();
	}

	public static InvoiceGateway forInvoice() {
		return new InvoiceGatewayImpl();
	}

	public static ClientGateway forClient() {
		return new ClientGatewayImpl();
	}

	public static VehicleGateway forVehicle() {
		return new VehicleGatewayImpl();
	}

	public static ContractGateway forContract() {
		return new ContractGatewayImpl();
	}

	public static ProfessionalGroupGateway forProfessionalGroup() {
		return new ProfessionalGroupGatewayImpl();
	}

	public static PayrollGateway forPayRoll() {
		return new PayrollGatewayImpl();
	}

	public static ContractTypeGateway forContractType() {
		return new ContractTypeGatewayImpl();
	}
}
