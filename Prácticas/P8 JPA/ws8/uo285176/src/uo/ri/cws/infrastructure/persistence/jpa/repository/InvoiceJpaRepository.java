package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import uo.ri.cws.application.repository.InvoiceRepository;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;
import uo.ri.util.exception.NotYetImplementedException;

public class InvoiceJpaRepository extends BaseJpaRepository<Invoice>
	implements InvoiceRepository {

    @Override
    public Optional<Invoice> findByNumber(Long numero) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public Long getNextInvoiceNumber() {
	// Comprobar que no hay un mecanico con el mismo dni
	TypedQuery<Long> tq = Jpa.getManager()
		.createNamedQuery("Invoice.getNextInvoiceNumber", Long.class);

	Long aux = null;
	// Obtenemos los resultados NO USAR getSingleResult()
	List<Long> result = tq.getResultList();

	if (!result.isEmpty())
	    aux = result.get(0);

	return aux;
    }

}
