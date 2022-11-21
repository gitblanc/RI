package uo.ri.cws.domain;

public class Associations {

    public static class Own {

	public static void link(Client client, Vehicle vehicle) {
	    vehicle._setClient(client);// método de paquete(empieza por _)
	    client._getVehicles().add(vehicle);
	}

	public static void unlink(Client client, Vehicle vehicle) {
	    client._getVehicles().remove(vehicle);
	    vehicle._setClient(null);
	}

    }

    public static class Classify {

	public static void link(VehicleType vehicleType, Vehicle vehicle) {
	    vehicle._setVehicleType(vehicleType);
	    vehicleType._getVehicles().add(vehicle);
	}

	public static void unlink(VehicleType vehicleType, Vehicle vehicle) {
	    vehicleType._getVehicles().remove(vehicle);
	    vehicle._setVehicleType(null);
	}

    }

    public static class Pay {

	public static void link(Client client, PaymentMean pm) {
	    pm._setClient(client);
	    client._getPaymentMeans().add(pm);
	}

	public static void unlink(Client client, PaymentMean pm) {
	    client._getPaymentMeans().remove(pm);
	    pm._setClient(null);
	}

    }

    public static class Fix {

	public static void link(Vehicle vehicle, WorkOrder workOrder) {
	    workOrder._setVehicle(vehicle);
	    vehicle._getWorkOrders().add(workOrder);
	}

	public static void unlink(Vehicle vehicle, WorkOrder workOrder) {
	    vehicle._getWorkOrders().remove(workOrder);
	    workOrder._setVehicle(null);
	}

    }

    public static class ToInvoice {

	public static void link(Invoice invoice, WorkOrder workOrder) {
	    workOrder._setInvoice(invoice);
	    invoice._getWorkOrders().add(workOrder);
	}

	public static void unlink(Invoice invoice, WorkOrder workOrder) {
	    invoice._getWorkOrders().remove(workOrder);
	    workOrder._setInvoice(null);
	}
    }

    public static class ToCharge {

	public static void link(PaymentMean pm, Charge charge,
		Invoice invoice) {
	    charge._setInvoice(invoice);
	    charge._setPaymentMean(pm);

	    invoice._getCharges().add(charge);
	    pm._getCharges().add(charge);
	}

	public static void unlink(Charge charge) {
	    charge._getInvoice()._getCharges().remove(charge);
	    charge._getPaymentMean()._getCharges().remove(charge);
	    charge._setInvoice(null);
	    charge._setPaymentMean(null);
	}

    }

    public static class Assign {

	public static void link(Mechanic mechanic, WorkOrder workOrder) {
	    workOrder._setMechanic(mechanic);
	    mechanic._getAssigned().add(workOrder);
	}

	public static void unlink(Mechanic mechanic, WorkOrder workOrder) {
	    mechanic._getAssigned().remove(workOrder);
	    workOrder._setMechanic(null);
	}

    }

    public static class Intervene {

	public static void link(WorkOrder workOrder, Intervention intervention,
		Mechanic mechanic) {
	    intervention._setMechanic(mechanic);
	    intervention._setWorkOrder(workOrder);

	    mechanic._getInterventions().add(intervention);
	    workOrder._getInterventions().add(intervention);

	}

	public static void unlink(Intervention intervention) {
	    intervention.getMechanic()._getInterventions().remove(intervention);
	    intervention.getWorkOrder()._getInterventions()
		    .remove(intervention);

	    intervention._setWorkOrder(null);
	    intervention._setMechanic(null);
	}

    }

    public static class Substitute {

	public static void link(SparePart spare, Substitution sustitution,
		Intervention intervention) {
	    sustitution._setIntervention(intervention);
	    sustitution._setSparePart(spare);

	    intervention._getSubstitutions().add(sustitution);
	    spare._getSubstitutions().add(sustitution);
	}

	public static void unlink(Substitution sustitution) {
	    sustitution._getIntervention()._getSubstitutions()
		    .remove(sustitution);
	    sustitution._getSparePart()._getSubstitutions().remove(sustitution);
	    sustitution._setSparePart(null);
	    sustitution._setIntervention(null);
	}

    }

    // EXTENSION

    public static class Fire {

	public static void link(Contract contract) {
	    contract.getMechanic().get()._getTerminatedContracts()
		    .add(contract);
	    contract.setFiredMechanic(contract.getMechanic().get());
	    contract.getMechanic().get()._setContractInForce(null);
	}

	public static void unlink(Contract contract) {
	    contract.getMechanic().get()._getTerminatedContracts()
		    .remove(contract);
	    contract.setFiredMechanic(null);
	    contract.setMechanic(null);
	}

    }

    public static class Group {

	public static void link(Contract contract, ProfessionalGroup group) {
	    group._getContracts().add(contract);
	    contract._setGroup(group);
	}

	public static void unlink(Contract contract, ProfessionalGroup group) {
	    contract._setGroup(null);
	    group._getContracts().remove(contract);
	}

    }

    public static class Hire {

	public static void link(Contract contract, Mechanic mechanic) {
	    mechanic._setContractInForce(contract);
	    contract.setMechanic(mechanic);
	}

	public static void unlink(Contract contract, Mechanic mechanic) {
	    mechanic._setContractInForce(null);
	}

    }

    public static class Run {

	public static void link(Contract contract, Payroll payroll) {
	    payroll._setContract(contract);
	    contract._getPayrolls().add(payroll);
	}

	public static void unlink(Payroll payroll) {
	    payroll.getContract()._getPayrolls().remove(payroll);
	    payroll._setContract(null);
	}

    }

    public static class Type {

	public static void link(Contract contract, ContractType type) {
	    contract._setContractType(type);
	    type._getContracts().add(contract);
	}

	public static void unlink(Contract contract, ContractType type) {
	    contract._setContractType(null);
	    type._getContracts().remove(contract);
	}

    }

}
