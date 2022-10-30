package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class Mechanic {
    // natural attributes
    private String dni;
    private String surname;
    private String name;

    // accidental attributes
    private Set<WorkOrder> assigned = new HashSet<>();
    private Set<Intervention> interventions = new HashSet<>();

    public Mechanic(String dni, String nombre, String apell) {
	ArgumentChecks.isNotNull(dni, "The dni can't be null");
	ArgumentChecks.isNotNull(nombre, "The name can't be null");
	ArgumentChecks.isNotNull(apell, "The surname can't be null");
	ArgumentChecks.isNotEmpty(dni, "The dni can't be empty");
	ArgumentChecks.isNotEmpty(nombre, "The name can't be empty");
	ArgumentChecks.isNotEmpty(apell, "The surname can't be empty");
	this.dni = dni;
	this.name = nombre;
	this.surname = apell;
    }

    @Override
    public String toString() {
	return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
		+ name + "]";
    }

    @Override
    public int hashCode() {
	return Objects.hash(dni);
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Mechanic other = (Mechanic) obj;
	return Objects.equals(dni, other.dni);
    }

    public String getDni() {
	return dni;
    }

    public String getSurname() {
	return surname;
    }

    public String getName() {
	return name;
    }

    public Set<WorkOrder> getAssigned() {
	return new HashSet<>(assigned);
    }

    Set<WorkOrder> _getAssigned() {
	return assigned;
    }

    public Set<Intervention> getInterventions() {
	return new HashSet<>(interventions);
    }

    Set<Intervention> _getInterventions() {
	return interventions;
    }

}
