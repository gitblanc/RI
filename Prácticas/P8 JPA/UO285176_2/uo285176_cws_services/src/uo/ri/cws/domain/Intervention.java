package uo.ri.cws.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tinterventions", uniqueConstraints = @UniqueConstraint(columnNames = {
	"workOrder_id", "date" }))
public class Intervention extends BaseEntity {
    // natural attributes
    @Basic(optional = false)
    private LocalDateTime date;
    @Basic(optional = false)
    private int minutes;

    // accidental attributes
    @ManyToOne
    private WorkOrder workOrder;
    @ManyToOne
    private Mechanic mechanic;
    @OneToMany(mappedBy = "intervention")
    private Set<Substitution> substitutions = new HashSet<>();

    public Intervention() {
    }

    public Intervention(Mechanic mecanico, WorkOrder workOrder, int time) {
	ArgumentChecks.isNotNull(mecanico, "The mechanic can't be null");
	ArgumentChecks.isNotNull(workOrder, "The workOrder can't be null");
	ArgumentChecks.isTrue(time >= 0, "The time can't be negative");
	this.date = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
	this.minutes = time;
	Associations.Intervene.link(workOrder, this, mecanico);
    }

    public Intervention(Mechanic mechanic, WorkOrder wo,
	    LocalDateTime atStartOfDay, int i) {
	ArgumentChecks.isNotNull(mechanic, "The mechanic can't be null");
	ArgumentChecks.isNotNull(wo, "The workOrder can't be null");
	ArgumentChecks.isNotNull(atStartOfDay, "The date can't be null");
	ArgumentChecks.isTrue(i >= 0);
	Associations.Intervene.link(wo, this, mechanic);
	this.date = atStartOfDay.truncatedTo(ChronoUnit.MILLIS);
	this.minutes = i;
    }

    @Override
    public int hashCode() {
	return Objects.hash(date, mechanic, workOrder);
    }

    @Override
    public String toString() {
	return "Intervention [date=" + date + ", minutes=" + minutes
		+ ", workOrder=" + workOrder + ", mechanic=" + mechanic + "]";
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Intervention other = (Intervention) obj;
	return Objects.equals(date, other.date)
		&& Objects.equals(mechanic, other.mechanic)
		&& Objects.equals(workOrder, other.workOrder);
    }

    void _setWorkOrder(WorkOrder workOrder) {
	this.workOrder = workOrder;
    }

    void _setMechanic(Mechanic mechanic) {
	this.mechanic = mechanic;
    }

    public Set<Substitution> getSubstitutions() {
	return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
	return substitutions;
    }

    public LocalDateTime getDate() {
	return date;
    }

    public int getMinutes() {
	return minutes;
    }

    public WorkOrder getWorkOrder() {
	return workOrder;
    }

    public Mechanic getMechanic() {
	return mechanic;
    }

    public double getAmount() {
	return computeAmount();
    }

    private double computeAmount() {
	return getRepuestos() + getManoDeObra();
    }

    private double getManoDeObra() {
	int time = this.minutes;
	double pricePerMinute = workOrder.getVehicle().getVehicleType()
		.getPricePerHour() / 60.0;
	return Math.round(time * pricePerMinute * 100.00) / 100.00;
    }

    private double getRepuestos() {
	double res = 0;
	for (Substitution s : substitutions) {
	    res += s.getQuantity() * s.getSparePart().getPrice();
	}
	return Math.round(res * 100.00) / 100.00;
    }

}
