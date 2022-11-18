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
@Table(name = "tprofessionalgroups")
public class ProfessionalGroup extends BaseEntity {

    // atributos naturales
    @Column(unique = true)
    private String name;
    @Basic(optional = false)
    private double productivityBonusPercentage;
    @Basic(optional = false)
    private double trienniumPayment;

    // accidentales
    @OneToMany(mappedBy = "professionalGroup")
    private Set<Contract> contracts = new HashSet<>();

    ProfessionalGroup() {
    }

    public ProfessionalGroup(String name, double triennium,
	    double productivityRate) {
	ArgumentChecks.isNotNull(name);
	ArgumentChecks.isNotEmpty(name);
	ArgumentChecks.isTrue(productivityRate >= 0);
	ArgumentChecks.isTrue(triennium >= 0);
	this.name = name;
	this.productivityBonusPercentage = productivityRate;
	this.trienniumPayment = triennium;
    }

    public String getName() {
	return name;
    }

    public double getTrienniumPayment() {
	return trienniumPayment;
    }

    @Override
    public String toString() {
	return "ProfessionalGroup [name=" + name
		+ ", productivityBonusPercentage=" + productivityBonusPercentage
		+ ", trienniumPayment=" + trienniumPayment + "]";
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
	result = prime * result + Objects.hash(name,
		productivityBonusPercentage, trienniumPayment);
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
	ProfessionalGroup other = (ProfessionalGroup) obj;
	return Objects.equals(name, other.name)
		&& Double.doubleToLongBits(
			productivityBonusPercentage) == Double.doubleToLongBits(
				other.productivityBonusPercentage)
		&& Double.doubleToLongBits(trienniumPayment) == Double
			.doubleToLongBits(other.trienniumPayment);
    }

    public double getProductivityBonusPercentage() {
	return productivityBonusPercentage;
    }

    public void setName(String name2) {
	this.name = name2;
    }

    public void setProductivityRate(double productivityRate) {
	this.productivityBonusPercentage = productivityRate;
    }

    public void setTriennium(double trieniumSalary) {
	this.trienniumPayment = trieniumSalary;
    }

}
