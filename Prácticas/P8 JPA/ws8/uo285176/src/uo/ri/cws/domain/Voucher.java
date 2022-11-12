package uo.ri.cws.domain;

import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

	/**
	 * Augments the accumulated (super.pay(amount) ) and decrements the available
	 * 
	 * @throws IllegalStateException if not enough available to pay
	 */
	@Override
	public void pay(double amount) {
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
		return Double.doubleToLongBits(available) == Double.doubleToLongBits(other.available)
				&& Objects.equals(code, other.code) && Objects.equals(description, other.description);
	}

	@Override
	public String toString() {
		return "Voucher [code=" + code + ", available=" + available + ", description=" + description + "]";
	}

}
