/**
 * 
 */
package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.util.exception.NotYetImplementedException;

/**
 * @author UO285176
 *
 */
public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
	implements PayrollRepository {

    @Override
    public List<Payroll> findByContract(String contractId) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public List<Payroll> findCurrentMonthPayrolls() {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public Optional<Payroll> findCurrentMonthByContractId(String contractId) {
	throw new NotYetImplementedException("SIN HACER");
    }

}
