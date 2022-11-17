package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PaymentMeanRepository;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.util.exception.NotYetImplementedException;

public class PaymentMeanJpaRepository extends BaseJpaRepository<PaymentMean>
	implements PaymentMeanRepository {

    @Override
    public List<PaymentMean> findPaymentMeansByClientId(String id) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public List<PaymentMean> findPaymentMeansByInvoiceId(String idFactura) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public List<PaymentMean> findByClientId(String id) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public Object[] findAggregateVoucherDataByClientId(String id) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public Optional<CreditCard> findCreditCardByNumber(String pan) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public List<Voucher> findVouchersByClientId(String id) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public Optional<Voucher> findVoucherByCode(String code) {
	throw new NotYetImplementedException("SIN HACER");
    }

}
