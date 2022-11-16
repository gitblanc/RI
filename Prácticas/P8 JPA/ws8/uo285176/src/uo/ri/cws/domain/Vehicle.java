package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tvehicles")
public class Vehicle extends BaseEntity {

    @Column(unique = true)
    private String plateNumber;
    @Basic(optional = false)
    private String brand;
    @Basic(optional = false)
    private String model;

    /*
     * Atributo accidental
     */
    @ManyToOne
    private Client client;
    @ManyToOne
    private VehicleType vehicleType;
    @OneToMany(mappedBy = "vehicle")
    private Set<WorkOrder> workOrders = new HashSet<>();

    Vehicle() {
    }

    public Vehicle(String plate, String marca, String modelo) {
	ArgumentChecks.isNotNull(plate, "The plateNumber can't be null");
	ArgumentChecks.isNotNull(marca, "The make can't be null");
	ArgumentChecks.isNotNull(modelo, "The model can't be null");
	ArgumentChecks.isNotEmpty(plate, "The plateNumber can't be empty");
	ArgumentChecks.isNotEmpty(marca, "The make can't be empty");
	ArgumentChecks.isNotNull(modelo, "The model can't be empty");
	this.plateNumber = plate;
	this.brand = marca;
	this.model = modelo;
    }

    public Vehicle(String plate) {
	ArgumentChecks.isNotNull(plate, "The plateNumber can't be null");
	ArgumentChecks.isNotEmpty(plate, "The plateNumber can't be empty");
	this.plateNumber = plate;
    }

    public String getPlateNumber() {
	return plateNumber;
    }

    public String getMake() {
	return brand;
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
	return "Vehicle [plateNumber=" + plateNumber + ", brand=" + brand
		+ ", model=" + model + "]";
    }

    public Client getClient() {
	return client;
    }

    void _setClient(Client client) {
	this.client = client;

    }

    public VehicleType getVehicleType() {
	return vehicleType;
    }

    void _setVehicleType(VehicleType type) {
	this.vehicleType = type;
    }

    public Set<WorkOrder> getWorkOrders() {
	return new HashSet<>(workOrders);
    }

    Set<WorkOrder> _getWorkOrders() {
	return this.workOrders;
    }
}
