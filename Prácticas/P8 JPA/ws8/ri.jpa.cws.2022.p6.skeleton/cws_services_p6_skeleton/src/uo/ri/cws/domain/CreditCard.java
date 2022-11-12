package uo.ri.cws.domain;

import java.time.LocalDate;

public class CreditCard extends PaymentMean {
    private String number;
    private String type;
    private LocalDate validThru;

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
