package uo.ri.cws.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tspareparts")
public class SparePart extends BaseEntity {
	// natural attributes
	@Column(unique = true)
	private String code;
	@Basic(optional = false)
	private String description;
	@Basic(optional = false)
	private double price;

	// accidental attributes
	@OneToMany(mappedBy="sparePart")
	private Set<Substitution> substitutions = new HashSet<>();

	public SparePart() {
	}
	
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
		return new HashSet<>(_getSubstitutions());
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
