package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import uo.ri.cws.domain.Invoice.InvoiceState;
import uo.ri.cws.domain.base.BaseEntity;
import uo.ri.util.assertion.ArgumentChecks;
import uo.ri.util.assertion.Assert;

@Entity
@Table(name = "tcharges")
public class Charge extends BaseEntity {
    @Basic(optional = false)
    // natural attributes
    private double amount = 0.0;

    // accidental attributes
    @ManyToOne
    private Invoice invoice;
    @ManyToOne
    private PaymentMean paymentMean;

    public Charge() {
    }

    public Charge(Invoice invoice, PaymentMean paymentMean, double amount) {
	ArgumentChecks.isNotNull(invoice, "The mechanic can't be null");
	ArgumentChecks.isNotNull(paymentMean, "The workOrder can't be null");
	ArgumentChecks.isTrue(amount >= 0, "The amount can't be negative");
	// store the amount
	this.amount = amount;
	// link invoice, this and paymentMean
	Associations.ToCharge.link(paymentMean, this, invoice);
	// increment the paymentMean accumulated -> paymentMean.pay( amount )
	getPaymentMean().pay(amount);
    }

    /**
     * Unlinks this charge and restores the accumulated to the payment mean
     * 
     * @throws IllegalStateException if the invoice is already settled
     */
    public void rewind() {
	// asserts the invoice is not in PAID status
	Assert.isTrue(!invoice.getState().equals(InvoiceState.PAID));
	// decrements the payment mean accumulated ( paymentMean.pay( -amount) )
	this.paymentMean.pay(-amount);
	// unlinks invoice, this and paymentMean
	Associations.ToCharge.unlink(this);

    }

    public Invoice getInvoice() {
	return this.invoice;
    }

    Invoice _getInvoice() {
	return this.invoice;
    }

    void _setInvoice(Invoice invoice) {
	this.invoice = invoice;
    }

    public PaymentMean getPaymentMean() {
	return this.paymentMean;
    }

    PaymentMean _getPaymentMean() {
	return this.paymentMean;
    }

    void _setPaymentMean(PaymentMean p) {
	this.paymentMean = p;
    }

    public double getAmount() {
	return amount;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = super.hashCode();
	result = prime * result + Objects.hash(amount);
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
	Charge other = (Charge) obj;
	return Double.doubleToLongBits(amount) == Double
		.doubleToLongBits(other.amount);
    }

    @Override
    public String toString() {
	return "Charge [amount=" + amount + "]";
    }

}
