/**
 * 
 */
package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
    private double productivityRate;
    @Basic(optional = false)
    private double trienniumSalary;

    ProfessionalGroup() {
    }

    public ProfessionalGroup(String name, double productivityRate,
	    double triennium) {
	ArgumentChecks.isNotNull(name);
	ArgumentChecks.isNotEmpty(name);
	ArgumentChecks.isTrue(productivityRate >= 0);
	ArgumentChecks.isTrue(triennium >= 0);
	this.name = name;
	this.productivityRate = productivityRate;
	this.trienniumSalary = triennium;
    }

    public String getName() {
	return name;
    }

    public double getProductivityRate() {
	return productivityRate;
    }

    public double getTrienniumSalary() {
	return trienniumSalary;
    }

    @Override
    public String toString() {
	return "ProfessionalGroup [name=" + name + ", productivityRate="
		+ productivityRate + ", trienniumSalary=" + trienniumSalary
		+ "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result
		+ Objects.hash(name, productivityRate, trienniumSalary);
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
		&& Double.doubleToLongBits(productivityRate) == Double
			.doubleToLongBits(other.productivityRate)
		&& Double.doubleToLongBits(trienniumSalary) == Double
			.doubleToLongBits(other.trienniumSalary);
    }

}
