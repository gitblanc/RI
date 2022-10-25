package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class VehicleType {
    // natural attributes
    private String name;
    private double pricePerHour;

    // accidental attributes
    private Set<Vehicle> vehicles = new HashSet<>();

    public VehicleType(String tipo, double precio) {
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
