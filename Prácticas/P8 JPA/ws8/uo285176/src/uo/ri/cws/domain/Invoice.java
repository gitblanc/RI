package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.math.Round;

@Entity
@Table(name = "tinvoices")
public class Invoice extends BaseEntity {
	public enum InvoiceState {
		NOT_YET_PAID, PAID
	}

	// natural attributes
	@Column(unique = true)
	private Long number;
	@Basic(optional = false)
	private LocalDate date;
	@Basic(optional = false)
	private double amount;

	@Basic(optional = false)
	private double vat;
	@Basic(optional = false)
	private InvoiceState state = InvoiceState.NOT_YET_PAID;

	// accidental attributes
	@OneToMany(mappedBy = "invoice")
	private Set<WorkOrder> workOrders = new HashSet<>();
	@OneToMany() // falta
	private Set<Charge> charges = new HashSet<>();

	Invoice() {
	}

	public Invoice(Long number) {
		// call full constructor with sensible defaults
		this(number, LocalDate.now());
	}

	public Invoice(Long number, LocalDate date) {
		// call full constructor with sensible defaults
		this(number, date, List.of());
	}

	public Invoice(Long number, List<WorkOrder> workOrders) {
		this(number, LocalDate.now(), workOrders);
	}

	// full constructor
	public Invoice(Long number, LocalDate date, List<WorkOrder> workOrders) {
		// check arguments (always), through IllegalArgumentException
		ArgumentChecks.isNotNull(number, "The number can't be null");
		ArgumentChecks.isNotNull(date, "The date can't be null");
		ArgumentChecks.isNotNull(workOrders, "The workorders can't be null");
		// store the number
		this.number = number;
		// store a copy of the date
		this.date = date;
		// add every work order calling addWorkOrder( w )
		for (WorkOrder w : workOrders) {
			addWorkOrder(w);
		}
	}

	/**
	 * Computes amount and vat (vat depends on the date)
	 */
	private void computeAmount() {
		double newAmount = 0.0;
		for (WorkOrder w : workOrders) {
			newAmount += w.getAmount();
		}

		this.vat = LocalDate.parse("2012-07-01").isBefore(this.date) ? 21.0 : 18.0;
		this.amount = Round.twoCents(newAmount * (1 + this.vat / 100));
	}

	/**
	 * Adds (double links) the workOrder to the invoice and updates the amount and
	 * vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void addWorkOrder(WorkOrder workOrder) {
//		if (this.state.equals(InvoiceState.NOT_YET_PAID))
//			throw new IllegalStateException();
		Associations.ToInvoice.link(this, workOrder);
		computeAmount();
	}

	/**
	 * Removes a work order from the invoice and recomputes amount and vat
	 * 
	 * @param workOrder
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if the invoice status is not NOT_YET_PAID
	 */
	public void removeWorkOrder(WorkOrder workOrder) {
//		if (this.state.equals(InvoiceState.NOT_YET_PAID))
//			throw new IllegalStateException();
		Associations.ToInvoice.unlink(this, workOrder);
		computeAmount();
	}

	/**
	 * Marks the invoice as PAID, but
	 * 
	 * @throws IllegalStateException if - Is already settled - Or the amounts paid
	 *                               with charges to payment means do not cover the
	 *                               total of the invoice
	 */
	public void settle() {

	}

	public Set<WorkOrder> getWorkOrders() {
		return new HashSet<>(workOrders);
	}

	Set<WorkOrder> _getWorkOrders() {
		return this.workOrders;
	}

	public Set<Charge> getCharges() {
		return new HashSet<>(charges);
	}

	Set<Charge> _getCharges() {
		return charges;
	}

	public Long getNumber() {
		return number;
	}

	public LocalDate getDate() {
		return date;
	}

	public double getAmount() {
		return amount;
	}

	public double getVat() {
		return vat;
	}

	public InvoiceState getState() {
		return state;
	}

	@Override
	public String toString() {
		return "Invoice [number=" + number + ", date=" + date + ", amount=" + amount + ", vat=" + vat + ", state="
				+ state + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(amount, date, number, state, vat);
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
		Invoice other = (Invoice) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount)
				&& Objects.equals(date, other.date) && Objects.equals(number, other.number) && state == other.state
				&& Double.doubleToLongBits(vat) == Double.doubleToLongBits(other.vat);
	}
}
