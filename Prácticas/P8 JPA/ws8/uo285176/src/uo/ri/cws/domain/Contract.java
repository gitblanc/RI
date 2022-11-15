/**
 * 
 */
package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
@Entity
@Table(name = "tcontracts", uniqueConstraints = @UniqueConstraint(columnNames = {
	"mechanic_id", "startdate" }))
public class Contract extends BaseEntity {

    public enum ContractState {
	IN_FORCE, TERMINATED
    }

    // naturales
    @Basic(optional = false)
    private LocalDate startDate;
    @Basic(optional = false)
    private double annualBaseWage;
    @Basic(optional = false)
    private LocalDate endDate;
    @Basic(optional = false)
    private double settlement;
    @Enumerated(EnumType.STRING)
    @Basic(optional = false)
    private ContractState state;

    // accidentales

    @OneToOne(mappedBy = "contract")
    private Mechanic mechanic;
    @ManyToOne
    private Mechanic firedMechanic;// CUIDADO, no es clave
    @ManyToOne
    private ContractType contractType;
    @ManyToOne
    private ProfessionalGroup group;

    Contract() {
    }

    public Contract(Mechanic mechanic, ContractType type,
	    ProfessionalGroup group, LocalDate endDate, double wage) {
	ArgumentChecks.isNotNull(mechanic);
	ArgumentChecks.isNotNull(type);
	ArgumentChecks.isNotNull(group);
	ArgumentChecks.isNotNull(endDate);
	ArgumentChecks.isTrue(wage >= 0);
	this.mechanic = mechanic;// FALTA link
	this.contractType = type;// FALTA link
	this.group = group;// FALTA link
	this.startDate = LocalDate.now();
	this.endDate = endDate;
	this.annualBaseWage = wage;
    }

    public LocalDate getStartDate() {
	return startDate;
    }

    public void setStartDate(LocalDate startDate) {
	this.startDate = startDate;
    }

    public double getAnnualBaseWage() {
	return annualBaseWage;
    }

    public void setAnnualBaseWage(double annualBaseWage) {
	this.annualBaseWage = annualBaseWage;
    }

    public LocalDate getEndDate() {
	return endDate;
    }

    public void setEndDate(LocalDate endDate) {
	this.endDate = endDate;
    }

    public double getSettlement() {
	return settlement;
    }

    public void setSettlement(double settlement) {
	this.settlement = settlement;
    }

    public ContractState getState() {
	return state;
    }

    public void setState(ContractState state) {
	this.state = state;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    public void setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    public Mechanic getFiredMechanic() {
	return firedMechanic;
    }

    public void setFiredMechanic(Mechanic firedMechanic) {
	this.firedMechanic = firedMechanic;
    }

    public ContractType getContractType() {
	return contractType;
    }

    public void setContractType(ContractType contractType) {
	this.contractType = contractType;
    }

    public ProfessionalGroup getGroup() {
	return group;
    }

    public void setGroup(ProfessionalGroup group) {
	this.group = group;
    }

    @Override
    public String toString() {
	return "Contract [startDate=" + startDate + ", annualBaseWage="
		+ annualBaseWage + ", endDate=" + endDate + ", settlement="
		+ settlement + ", state=" + state + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(annualBaseWage, endDate,
		settlement, startDate, state);
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
	Contract other = (Contract) obj;
	return Double.doubleToLongBits(annualBaseWage) == Double
		.doubleToLongBits(other.annualBaseWage)
		&& Objects.equals(endDate, other.endDate)
		&& Double.doubleToLongBits(settlement) == Double
			.doubleToLongBits(other.settlement)
		&& Objects.equals(startDate, other.startDate)
		&& state == other.state;
    }

}
