package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
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
    @OneToMany(mappedBy = "firedMechanic")
    private Set<Contract> terminatedContracts = new HashSet<>();// lista de
								// contratos
    // terminados
    @OneToOne(mappedBy = "mechanic")
    private Contract contract;// contrato en vigor

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
	this.name = "";
	this.surname = "";
    }

    @Override
    public String toString() {
	return "Mechanic [dni=" + dni + ", surname=" + surname + ", name="
		+ name + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(dni, name, surname);
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (!super.equals(obj))
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Mechanic other = (Mechanic) obj;
	return Objects.equals(dni, other.dni)
		&& Objects.equals(name, other.name)
		&& Objects.equals(surname, other.surname);
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

    public Optional<Contract> getContractInForce() {
	return Optional.ofNullable(contract);
    }

    public Set<Contract> getTerminatedContracts() {
	return new HashSet<Contract>(terminatedContracts);
    }

    public Set<Contract> _getTerminatedContracts() {
	return terminatedContracts;
    }

    public boolean isInForce() {
	return contract != null;
    }

    public void _setContractInForce(Contract c) {
	this.contract = c;
    }

}
