package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Vehicle {

    private String plateNumber;
    private String make;
    private String model;

    /*
     * Atributo accidental
     */
    private Client client;
    private VehicleType type;
    private Set<WorkOrder> workOrders = new HashSet<>();

    public Vehicle(String plate, String marca, String modelo) {
	ArgumentChecks.isNotNull(plate, "The plateNumber can't be null");
	ArgumentChecks.isNotNull(marca, "The make can't be null");
	ArgumentChecks.isNotNull(modelo, "The model can't be null");
	ArgumentChecks.isNotEmpty(plate, "The plateNumber can't be empty");
	ArgumentChecks.isNotEmpty(marca, "The make can't be empty");
	ArgumentChecks.isNotNull(modelo, "The model can't be empty");
	this.plateNumber = plate;
	this.make = marca;
	this.model = modelo;
    }

    public String getPlateNumber() {
	return plateNumber;
    }

    public String getMake() {
	return make;
    }

    public String getModel() {
	return model;
    }

    @Override
    public int hashCode() {
	return Objects.hash(plateNumber);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Vehicle other = (Vehicle) obj;
	return Objects.equals(plateNumber, other.plateNumber);
    }

    /*
     * En el toString no se meten atributos accidentales
     */
    @Override
    public String toString() {
	return "Vehicle [plateNumber=" + plateNumber + ", make=" + make
		+ ", model=" + model + "]";
    }

    public Client getClient() {
	return client;
    }

    void _setClient(Client client) {
	this.client = client;

    }

    public VehicleType getVehicleType() {
	return type;
    }

    void _setVehicleType(VehicleType type) {
	this.type = type;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
	return this.workOrders;
    }
}
