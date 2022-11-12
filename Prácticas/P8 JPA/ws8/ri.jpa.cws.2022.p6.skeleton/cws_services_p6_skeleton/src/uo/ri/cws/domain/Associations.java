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

	}

	public static void unlink(Invoice invoice, WorkOrder workOrder) {

	}
    }

    public static class ToCharge {

	public static void link(PaymentMean pm, Charge charge,
		Invoice inovice) {
	    // TODO Auto-generated method stub
	}

	public static void unlink(Charge charge) {
	    // TODO Auto-generated method stub
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
	    intervention
		    .getWorkOrder()._getInterventions().remove(intervention);

	    intervention._setWorkOrder(null);
	    intervention._setMechanic(null);
	}

    }

    public static class Substitute {

	public static void unlink(SparePart spare, Substitution sustitution,
		Intervention intervention) {
	    // TODO Auto-generated method stub
	}

	public static void unlink(Substitution sustitution) {
	    // TODO Auto-generated method stub
	}

    }

}
