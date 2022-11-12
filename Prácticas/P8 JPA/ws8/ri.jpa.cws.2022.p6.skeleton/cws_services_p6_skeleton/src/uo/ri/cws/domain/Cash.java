package uo.ri.cws.domain;

public class Cash extends PaymentMean {

    public Cash(Client c) {
	super();
	Associations.Pay.link(c, this);
    }
}
