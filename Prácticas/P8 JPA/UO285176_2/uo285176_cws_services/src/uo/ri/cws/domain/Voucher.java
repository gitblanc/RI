package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tvouchers")
public class Voucher extends PaymentMean {
    @Column(unique = true)
    private String code;
    @Basic(optional = false)
    private double available = 0.0;
    @Basic(optional = false)
    private String description;

    public Voucher() {
    }

    public Voucher(String code, String descrip, double cash) {
	ArgumentChecks.isNotNull(code);
	ArgumentChecks.isNotEmpty(code);
	ArgumentChecks.isNotNull(descrip);
	ArgumentChecks.isNotEmpty(descrip);
	ArgumentChecks.isTrue(cash >= 0);
	this.code = code;
	this.description = descrip;
	this.available = cash;
    }

    public Voucher(String code, double av) {
	ArgumentChecks.isNotNull(code);
	ArgumentChecks.isNotEmpty(code);
	ArgumentChecks.isTrue(av >= 0);
	this.code = code;
	this.available = av;
	this.description = "no-description";
    }

    /**
     * Augments the accumulated (super.pay(amount) ) and decrements the
     * available
     * 
     * @throws IllegalStateException if not enough available to pay
     */
    @Override
    public void pay(double amount) {
	if (available - amount < 0)
	    throw new IllegalStateException();
	super.pay(amount);
	this.available -= amount;
    }

    public String getCode() {
	return code;
    }

    public double getAvailable() {
	return available;
    }

    public String getDescription() {
	return description;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(available, code, description);
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
	Voucher other = (Voucher) obj;
	return Double.doubleToLongBits(available) == Double
		.doubleToLongBits(other.available)
		&& Objects.equals(code, other.code)
		&& Objects.equals(description, other.description);
    }

    @Override
    public String toString() {
	return "Voucher [code=" + code + ", available=" + available
		+ ", description=" + description + "]";
    }

}
