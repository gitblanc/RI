package uo.ri.cws.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import uo.ri.util.assertion.ArgumentChecks;

@Entity
@Table(name = "tcreditcards")
@Inheritance(strategy = InheritanceType.JOINED)
public class CreditCard extends PaymentMean {
	@Column(unique = true)
	private String number;
	@Basic(optional = false)
	private String type;
	@Basic(optional = false)
	private LocalDate validThru;

	public CreditCard() {
	}

	public CreditCard(String string) {
		super();
		// FALTAN VALIDACIONES
		ArgumentChecks.isNotNull(string);
		ArgumentChecks.isNotEmpty(string);
		this.number = string;
		this.type = "UNKNOWN";
		this.validThru = LocalDate.now().plusDays(1);
	}

	public String getNumber() {
		return number;
	}

	public String getType() {
		return type;
	}

	public LocalDate getValidThru() {
		return validThru;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(number, type, validThru);
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
		CreditCard other = (CreditCard) obj;
		return Objects.equals(number, other.number) && Objects.equals(type, other.type)
				&& Objects.equals(validThru, other.validThru);
	}

	@Override
	public String toString() {
		return "CreditCard [number=" + number + ", type=" + type + ", validThru=" + validThru + "]";
	}

}
