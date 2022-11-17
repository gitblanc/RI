/**
 * 
 */
package uo.ri.cws.domain;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.WorkOrder.WorkOrderState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

/**
 * @author UO285176
 *
 */
@Entity
@Table(name = "tpayrolls", uniqueConstraints = @UniqueConstraint(columnNames = {
	"contract_id", "date" }))
public class Payroll extends BaseEntity {

    // naturales
    @Basic(optional = false)
    private LocalDate date;
    @Basic(optional = false)
    private double bonus;
    @Basic(optional = false)
    private double incomeTax;
    @Basic(optional = false)
    private double monthlyWage;
    @Basic(optional = false)
    private double nic;
    @Basic(optional = false)
    private double productivityBonus;
    @Basic(optional = false)
    private double trienniumPayment;

    // accidentales
    @ManyToOne
    private Contract contract;

    Payroll() {
    }

    public Payroll(Contract contract, LocalDate d, double monthlyWage,
	    double extra, double productivity, double trienniums, double tax,
	    double nic) {
	this.date = d;
	this.monthlyWage = monthlyWage;
	this.bonus = extra;
	this.productivityBonus = productivity;
	this.trienniumPayment = trienniums;
	this.incomeTax = tax;
	this.nic = nic;
	Associations.Run.link(contract, this);
    }

    public Payroll(Contract c) {
	ArgumentChecks.isNotNull(c);
	this.date = LocalDate.now();
	Associations.Run.link(c, this);
	calcularDatosPayroll();
    }

    public Payroll(Contract contract, LocalDate d) {
	ArgumentChecks.isNotNull(contract);
	ArgumentChecks.isNotNull(d);
	this.date = d;
	Associations.Run.link(contract, this);
	calcularDatosPayroll();
    }

    private void calcularDatosPayroll() {
	this.monthlyWage = Math.floor(contract.getAnnualBaseWage() / 14);
	calculateBonus();// bonus
	calculateProductivityBonus(contract.getMechanic().get().getAssigned(),
		contract.getProfessionalGroup());// plus de productividad
	calculateTrienniumPayment();// trienniums
	double grossWage = calculateGrossWage();
	calculateIncomeTax(grossWage, contract.getAnnualBaseWage());
	calculateNic(contract.getAnnualBaseWage());
    }

    private void calculateNic(double annualBaseWage) {
	double percentage = 0.05 * annualBaseWage;
	this.nic = Round.twoCents(percentage / 12);
    }

    public double calculateGrossWage() {
	return this.monthlyWage + this.bonus + this.productivityBonus
		+ this.trienniumPayment;
    }

    private void calculateIncomeTax(double grossWage, double annualBaseWage) {
	double res = 0.0;
	if (annualBaseWage <= 12450) {
	    res = 0.19;
	} else if (annualBaseWage <= 20200) {
	    res = 0.24;
	} else if (annualBaseWage <= 35200) {
	    res = 0.3;
	} else if (annualBaseWage <= 60000) {
	    res = 0.37;
	} else if (annualBaseWage <= 300000) {
	    res = 0.45;
	} else {
	    res = 0.47;
	}
	this.incomeTax = Round.twoCents(res * grossWage);// IRPF
    }

    private void calculateTrienniumPayment() {
	double tPayment = 0;

	// obtenemos la antiguedad del contrato
	long time = Period.between(contract.getStartDate(), this.date)
		.getYears();
	int numTrienniums = (int) Math.floor(time / 3);

	ProfessionalGroup group = contract.getProfessionalGroup();
	if (!group.getName().isEmpty()) {
	    double triennium = group.getTrienniumPayment();
	    tPayment = triennium * numTrienniums;
	}
	this.trienniumPayment = Math.floor(tPayment * 10);// math.floor?
    }

    private void calculateProductivityBonus(Set<WorkOrder> workOrders,
	    ProfessionalGroup group) {
	double amount = calculateAmount(workOrders);
	this.productivityBonus = obtainProductivityBonus(group, amount);
    }

    private double obtainProductivityBonus(ProfessionalGroup group,
	    double amount) {
	double productivityBonus = 0;
	// Obtenemos el porcentaje en función del grupo profesional y lo
	// aplicamos al amount
	if (!group.getName().isEmpty()) {
	    double percentage = group.getProductivityBonusPercentage();
	    productivityBonus = Math.floor(percentage * amount);
	}
	return Round.twoCents(productivityBonus / 1000);// Round.twoCents()
    }

    private double calculateAmount(Set<WorkOrder> workOrders) {
	double amount = 0;
	for (WorkOrder w : workOrders) {
	    boolean condDate = w.getDate().getMonth()
		    .equals(this.date.getMonth())
		    && w.getDate().getYear() == this.date.getYear();
	    boolean condState = w.getState().equals(WorkOrderState.INVOICED);

	    if (condDate && condState)
		amount += Math.floor(w.getAmount());
	}
	return Round.twoCents(amount);
    }

    private void calculateBonus() {
	if (this.date.getMonth().equals(Month.JUNE)
		|| this.date.getMonth().equals(Month.DECEMBER)) {
	    this.bonus = Math.floor(contract.getAnnualBaseWage() / 14);
	} else {
	    this.bonus = 0.0;
	}
    }

    public Contract getContract() {
	return this.contract;
    }

    public LocalDate getDate() {
	return date;
    }

    public double getBonus() {
	return bonus;
    }

    public double getIncomeTax() {
	return incomeTax;
    }

    public double getMonthlyWage() {
	return monthlyWage;
    }

    public double getNIC() {
	return nic;
    }

    public double getProductivityBonus() {
	return productivityBonus;
    }

    public double getTrienniumPayment() {
	return trienniumPayment;
    }

    public void _setContract(Contract c) {
	this.contract = c;
    }

    @Override
    public String toString() {
	return "Payroll [date=" + date + ", bonus=" + bonus + ", incomeTax="
		+ incomeTax + ", monthlyWage=" + monthlyWage + ", nic=" + nic
		+ ", productivityBonus=" + productivityBonus
		+ ", trienniumPayment=" + trienniumPayment + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(contract, date);
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
	Payroll other = (Payroll) obj;
	return Objects.equals(contract, other.contract)
		&& Objects.equals(date, other.date);
    }

}
