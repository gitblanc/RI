package uo.ri.cws.domain;

import uo.ri.util.assertion.ArgumentChecks;

public class Substitution {
    // natural attributes
    private int quantity;

    // accidental attributes
    private SparePart sparePart;
    private Intervention intervention;

    public Substitution(SparePart sparePart, Intervention intervention,
	    int quantity) {
	ArgumentChecks.isNotNull(sparePart, "The squarePart can't be null");
	ArgumentChecks
		.isNotNull(intervention, "The intervention can't be null");
	this.sparePart = sparePart;
	this.intervention = intervention;
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
	return sparePart;
    }

    public Intervention getIntervention() {
	return intervention;
    }

}
