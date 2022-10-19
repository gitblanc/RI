/**
 * 
 */
package uo.ri.cws.application.persistence.invoice.impl;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.persistence.invoice.InvoiceGateway;

/**
 * @author UO285176
 *
 */
public class InvoiceGatewayImpl implements InvoiceGateway {
	@Override
	public void add(InvoiceDALDto t) {
	}

	@Override
	public void remove(String id) {
	}

	@Override
	public void update(InvoiceDALDto t) {
	}

	@Override
	public Optional<InvoiceDALDto> findById(String id) {
		return null;
	}

	@Override
	public List<InvoiceDALDto> findAll() {
		return null;
	}

	@Override
	public Optional<InvoiceDALDto> findByNumber(Long number) {
		return null;
	}

	@Override
	public Long getNextInvoiceNumber() {
		return null;
	}
}
