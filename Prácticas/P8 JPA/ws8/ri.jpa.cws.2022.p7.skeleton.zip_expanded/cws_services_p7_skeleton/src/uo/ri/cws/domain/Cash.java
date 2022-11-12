package uo.ri.cws.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tcashes")
public class Cash extends PaymentMean{
	//La identidad natural de cash es el cliente
	Cash(){}
	
    public Cash(Client c) {
	super();
	Associations.Pay.link(c, this);
    }
}
