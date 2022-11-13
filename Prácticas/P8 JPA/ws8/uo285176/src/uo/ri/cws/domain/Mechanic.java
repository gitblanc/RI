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
@Table(name = "tmechanics")
public class Mechanic extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String dni;
	@Basic(optional = false)
	private String surname;
	@Basic(optional = false)
	private String name;

	// accidental attributes
	@OneToMany(mappedBy = "mechanic")
	private Set<WorkOrder> assigned = new HashSet<>();
	@OneToMany(mappedBy = "mechanic")
	private Set<Intervention> interventions = new HashSet<>();

	public Mechanic() {
	}

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

	public Mechanic(String dni) {
		ArgumentChecks.isNotNull(dni, "The dni can't be null");
		ArgumentChecks.isNotEmpty(dni, "The dni can't be empty");
		this.dni = dni;
	}

	@Override
	public String toString() {
		return "Mechanic [dni=" + dni + ", surname=" + surname + ", name=" + name + "]";
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

	public void setName(String name2) {
		this.name = name2;
	}

	public void setSurname(String surname2) {
		this.surname = surname2;
	}

}
