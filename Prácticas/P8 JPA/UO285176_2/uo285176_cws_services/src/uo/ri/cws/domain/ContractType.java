/**
 * 
 */
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

/**
 * @author UO285176
 *
 */
@Entity
@Table(name = "tcontracttypes")
public class ContractType extends BaseEntity {

    // atributos naturales
    @Column(unique = true)
    private String name;
    @Basic(optional = false)
    private double compensationDays;

    // accidentales
    @OneToMany(mappedBy = "contractType")
    private Set<Contract> contracts = new HashSet<>();

    ContractType() {
    }

    public ContractType(String name, double compensationDays) {
	ArgumentChecks.isNotNull(name);
	ArgumentChecks.isNotEmpty(name);
	ArgumentChecks.isTrue(compensationDays >= 0);
	this.name = name;
	this.compensationDays = compensationDays;
    }

    public String getName() {
	return name;
    }

    public double getCompensationDays() {
	return compensationDays;
    }

    @Override
    public String toString() {
	return "ContractType [name=" + name + ", compensationDays="
		+ compensationDays + "]";
    }

    public Set<Contract> getContracts() {
	return new HashSet<Contract>(contracts);
    }

    Set<Contract> _getContracts() {
	return contracts;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(compensationDays, name);
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
	ContractType other = (ContractType) obj;
	return Double.doubleToLongBits(compensationDays) == Double
		.doubleToLongBits(other.compensationDays)
		&& Objects.equals(name, other.name);
    }

}
