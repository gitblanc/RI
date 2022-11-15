package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tworkorders", uniqueConstraints = @UniqueConstraint(columnNames = { "date", "vehicle_id" }))
public class WorkOrder extends BaseEntity {
	public enum WorkOrderState {
		OPEN, ASSIGNED, FINISHED, INVOICED
	}

	// natural attributes
	@Basic(optional = false)
	private LocalDateTime date;
	@Basic(optional = false)
	private String description;
	@Basic(optional = false)
	private double amount = 0.0;
	@Enumerated(EnumType.STRING)
	@Basic(optional = false)
	private WorkOrderState state = WorkOrderState.OPEN;

	// accidental attributes
	@ManyToOne(optional = false)
	private Vehicle vehicle;
	@ManyToOne(optional = false)
	private Mechanic mechanic;
	@ManyToOne(optional = false)
	private Invoice invoice;
	@OneToMany(mappedBy = "workOrder")
	private Set<Intervention> interventions = new HashSet<>();

	public WorkOrder() {
	}

	public WorkOrder(Vehicle v, String desc) {
		ArgumentChecks.isNotNull(v, "The vehicle can't be null");
		ArgumentChecks.isNotNull(desc, "The description can't be null");
		ArgumentChecks.isNotEmpty(desc, "The description can't be empty");
		this.description = desc;
		this.date = LocalDateTime.now();
		// this.vehicle = v; NO - clase dedicada
		Associations.Fix.link(v, this);
	}

	public WorkOrder(Vehicle vehicle) {
		Associations.Fix.link(vehicle, this);
	}

	@Override
	public String toString() {
		return "WorkOrder [date=" + date + ", vehicle=" + vehicle + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(date, vehicle);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WorkOrder other = (WorkOrder) obj;
		return Objects.equals(date, other.date) && Objects.equals(vehicle, other.vehicle);
	}

	/**
	 * Changes it to INVOICED state given the right conditions This method is called
	 * from Invoice.addWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not FINISHED, or - The
	 *                               work order is not linked with the invoice
	 */
	public void markAsInvoiced() {
		if (!this.state.equals(WorkOrderState.FINISHED) || this.invoice == null)
			throw new IllegalStateException();
		this.state = WorkOrderState.INVOICED;
	}

	/**
	 * Changes it to FINISHED state given the right conditions and computes the
	 * amount and unlinks from the previous mechanic.
	 *
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED state,
	 *                               or - The work order is not linked with a
	 *                               mechanic
	 */
	public void markAsFinished() {
		if (!this.state.equals(WorkOrderState.ASSIGNED) || this.mechanic == null)
			throw new IllegalStateException();
		this.state = WorkOrderState.FINISHED;
		this.amount = computeAmount();
	}

	private double computeAmount() {
		double newAmount = 0;
		for (Intervention i : interventions) {
			newAmount += i.getAmount();
		}
		return Math.round(newAmount * 100.00) / 100.00;
	}

	/**
	 * Changes it back to FINISHED state given the right conditions This method is
	 * called from Invoice.removeWorkOrder(...)
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not INVOICED, or - The
	 *                               work order is still linked with the invoice
	 */
	public void markBackToFinished() {
		if (!this.state.equals(WorkOrderState.INVOICED) || this.invoice != null)
			throw new IllegalStateException();
		this.state = WorkOrderState.FINISHED;
	}

	/**
	 * Links (assigns) the work order to a mechanic and then changes its state to
	 * ASSIGNED
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in OPEN state, or -
	 *                               The work order is already linked with another
	 *                               mechanic
	 */
	public void assignTo(Mechanic mechanic) {
		if (!this.state.equals(WorkOrderState.OPEN) || this.mechanic != null)
			throw new IllegalStateException();
		Associations.Assign.link(mechanic, this);
		this.state = WorkOrderState.ASSIGNED;
	}

	/**
	 * Unlinks (deassigns) the work order and the mechanic and then changes its
	 * state back to OPEN
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in ASSIGNED state
	 */
	public void desassign() {
		if (!this.state.equals(WorkOrderState.ASSIGNED))
			throw new IllegalStateException();
		Associations.Assign.unlink(mechanic, this);
		this.state = WorkOrderState.OPEN;
	}

	/**
	 * In order to assign a work order to another mechanic is first have to be moved
	 * back to OPEN state and unlink
	 * 
	 * @see UML_State diagrams on the problem statement document
	 * @throws IllegalStateException if - The work order is not in FINISHED state
	 */
	public void reopen() {
		if (!this.state.equals(WorkOrderState.FINISHED))
			throw new IllegalStateException("State not finished");
		Associations.Assign.unlink(mechanic, this);
		this.state = WorkOrderState.OPEN;
	}

	public Set<Intervention> getInterventions() {
		return new HashSet<>(interventions);
	}

	Set<Intervention> _getInterventions() {
		return interventions;
	}

	void _setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	void _setMechanic(Mechanic mechanic) {
		this.mechanic = mechanic;
	}

	void _setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public String getDescription() {
		return description;
	}

	public double getAmount() {
		return amount;
	}

	public WorkOrderState getState() {
		return state;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public Mechanic getMechanic() {
		return mechanic;
	}

	public Invoice getInvoice() {
		return invoice;
	}

	public boolean isInvoiced() {
		return this.state.equals(WorkOrderState.INVOICED);
	}

	public boolean isFinished() {
		return this.state.equals(WorkOrderState.FINISHED);
	}
}
