package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import uo.ri.util.assertion.ArgumentChecks;

public class SparePart {
    // natural attributes
    private String code;
    private String description;
    private double price;

    // accidental attributes
    private Set<Substitution> substitutions = new HashSet<>();

    public SparePart(String code, String desc, double price) {
	ArgumentChecks.isNotNull(code, "The code can't be null");
	ArgumentChecks.isNotNull(desc, "The description can't be null");
	ArgumentChecks.isNotEmpty(code, "The code can't be empty");
	ArgumentChecks.isNotEmpty(desc, "The description can't be empty");
	ArgumentChecks.isTrue(price > 0, "The price can't be negative");
	this.code = code;
	this.description = desc;
	this.price = price;
    }

    public Set<Substitution> getSubstitutions() {
	return new HashSet<>(substitutions);
    }

    Set<Substitution> _getSubstitutions() {
	return substitutions;
    }

    public String getCode() {
	return code;
    }

    public String getDescription() {
	return description;
    }

    public double getPrice() {
	return price;
    }

}
