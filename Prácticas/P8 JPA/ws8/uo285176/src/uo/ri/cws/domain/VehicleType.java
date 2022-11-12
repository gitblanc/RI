package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name="tvehicletypes")
public class VehicleType extends BaseEntity {
    // natural attributes
	@Column(unique=true) private String name;
	@Basic(optional=false) private double pricePerHour;

    // accidental attributes
	@OneToMany(mappedBy="type")
    private Set<Vehicle> vehicles = new HashSet<>();

	public VehicleType() {
	}
	
    public VehicleType(String tipo, double precio) {
	ArgumentChecks.isNotNull(tipo, "Tipo can't be null");
	ArgumentChecks.isNotEmpty(tipo, "Tipo can't be empty");
	ArgumentChecks.isTrue(precio > 0, "The price can't be negative");
	this.name = tipo;
	this.pricePerHour = precio;
    }

    @Override
    public String toString() {
	return "VehicleType [name=" + name + ", pricePerHour=" + pricePerHour
		+ "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(name);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	VehicleType other = (VehicleType) obj;
	return Objects.equals(name, other.name);
    }

    public Set<Vehicle> getVehicles() {
	return new HashSet<>(vehicles);
    }

    Set<Vehicle> _getVehicles() {
	return vehicles;
    }

    public String getName() {
	return name;
    }

    public double getPricePerHour() {
	return pricePerHour;
    }

}
