package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

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

}
