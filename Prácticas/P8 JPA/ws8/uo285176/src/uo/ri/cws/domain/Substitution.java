package uo.ri.cws.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tsubstitutions", uniqueConstraints = @UniqueConstraint(columnNames = {
	"sparepart_id", "intervention_id" }))
public class Substitution extends BaseEntity {
    // natural attributes
    @Basic(optional = false)
    private int quantity;

    // accidental attributes
    @ManyToOne(optional = false)
    private SparePart sparePart;
    @ManyToOne(optional = false)
    private Intervention intervention;

    public Substitution() {
    }

    public Substitution(SparePart sparePart, Intervention intervention,
	    int quantity) {
	ArgumentChecks.isNotNull(sparePart, "The squarePart can't be null");
	ArgumentChecks.isNotNull(intervention,
		"The intervention can't be null");
	ArgumentChecks.isTrue(quantity > 0);
	Associations.Substitute.link(sparePart, this, intervention);
	this.quantity = quantity;
    }

    void _setSparePart(SparePart sparePart) {
	this.sparePart = sparePart;
    }

    void _setIntervention(Intervention intervention) {
	this.intervention = intervention;
    }

    public int getQuantity() {
	return quantity;
    }

    public SparePart getSparePart() {
	return this.sparePart;
    }

    SparePart _getSparePart() {
	return this.sparePart;
    }

    public Intervention getIntervention() {
	return this.intervention;
    }

    Intervention _getIntervention() {
	return this.intervention;
    }

    public double getAmount() {
	return _getIntervention().getAmount();
    }

}
