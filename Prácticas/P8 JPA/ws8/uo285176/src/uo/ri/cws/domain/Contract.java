/**
 * 
 */
package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

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

    @OneToOne()
    private Mechanic mechanic;
    @ManyToOne
    private Mechanic firedMechanic;// CUIDADO, no es clave
    @ManyToOne
    private ContractType contractType;
    @ManyToOne
    private ProfessionalGroup professionalGroup;
    @OneToMany(mappedBy = "contract")
    private Set<Payroll> payrolls = new HashSet<>();

    Contract() {
    }

    public Contract(Mechanic mechanic, ContractType type,
	    ProfessionalGroup group, LocalDate endDate, double wage) {
	ArgumentChecks.isNotNull(mechanic);
	ArgumentChecks.isNotNull(type);
	ArgumentChecks.isNotNull(group);
	ArgumentChecks.isNotNull(endDate);
	ArgumentChecks.isTrue(wage >= 0);
	this.state = ContractState.IN_FORCE;
	this.startDate = LocalDate.now();
	this.endDate = endDate;
	this.annualBaseWage = wage;
	Associations.Type.link(this, type);
	Associations.Hire.link(this, mechanic);
	Associations.Group.link(this, group);
    }

    private void calculateSettlement() {
	int C = LocalDate.now().getYear() - getStartDate().getYear();
	double B = this.contractType.getCompensationDays();
	double A = (this.annualBaseWage / 365);
//	double A = calcularMedia();
	double aux = Math.floor(A * B * C);
	if (aux < 1) {// comprobamos el año completo
	    this.settlement = 0.00;
	} else {
	    this.settlement = aux;
	}
    }

    private double calcularMedia() {
	double media = 0;
	Payroll[] p = getPayrolls().toArray(new Payroll[getPayrolls().size()]);
	int cont = 0;
	for (int i = p.length - 1; cont == 12 || i > -1; i--, cont++) {
	    media += p[i].getTrienniumPayment();
	}
	media += professionalGroup.getTrienniumPayment();
	return Round.twoCents(media / 365);
    }

    public Contract(Mechanic mechanic, ContractType type,
	    ProfessionalGroup group, double wage) {
	ArgumentChecks.isNotNull(mechanic);
	ArgumentChecks.isNotNull(type);
	ArgumentChecks.isNotNull(group);
	ArgumentChecks.isTrue(wage >= 0);
	this.state = ContractState.IN_FORCE;
	this.startDate = LocalDate.now();
	this.annualBaseWage = wage;
	Associations.Type.link(this, type);
	Associations.Hire.link(this, mechanic);
	Associations.Group.link(this, group);
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

    public Optional<LocalDate> getEndDate() {
	return Optional.ofNullable(endDate);
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

    public Optional<Mechanic> getMechanic() {
	return Optional.ofNullable(mechanic);
    }

    public void setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    public Optional<Mechanic> getFiredMechanic() {
	return Optional.ofNullable(firedMechanic);
    }

    public void setFiredMechanic(Mechanic firedMechanic) {
	this.firedMechanic = firedMechanic;
    }

    public ContractType getContractType() {
	return contractType;
    }

    public void _setContractType(ContractType contractType) {
	this.contractType = contractType;
    }

    public ProfessionalGroup getProfessionalGroup() {
	return professionalGroup;
    }

    public void _setGroup(ProfessionalGroup group) {
	this.professionalGroup = group;
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

    public Set<Payroll> getPayrolls() {
	return new HashSet<Payroll>(payrolls);
    }

    Set<Payroll> _getPayrolls() {
	return this.payrolls;
    }

    public void terminate() {
	calculateSettlement();
	Associations.Fire.link(this);
	this.state = ContractState.TERMINATED;
    }

}
