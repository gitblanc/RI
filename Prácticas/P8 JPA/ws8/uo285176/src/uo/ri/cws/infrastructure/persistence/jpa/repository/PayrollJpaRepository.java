/**
 * 
 */
package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

/**
 * @author UO285176
 *
 */
public class PayrollJpaRepository extends BaseJpaRepository<Payroll>
	implements PayrollRepository {

    @Override
    public List<Payroll> findByContract(String contractId) {
	return Jpa.getManager()
		.createNamedQuery("Payroll.findByContract", Payroll.class)
		.setParameter(1, contractId).getResultList();
    }

    @Override
    public List<Payroll> findCurrentMonthPayrolls() {
	return Jpa.getManager()
		.createNamedQuery("Payroll.findCurrentMonthPayrolls",
			Payroll.class)
		.setParameter(1, LocalDate.now().getMonthValue())
		.setParameter(2, LocalDate.now().getYear()).getResultList();
    }

    @Override
    public Optional<Payroll> findCurrentMonthByContractId(String contractId) {
	TypedQuery<Payroll> tq = Jpa.getManager()
		.createNamedQuery("Payroll.findCurrentMonthByContractId",
			Payroll.class)
		.setParameter(1, contractId);

	Payroll p = null;
	// Obtenemos los resultados NO USAR getSingleResult()
	List<Payroll> result = tq.getResultList();

	if (!result.isEmpty())
	    p = result.get(0);

	return Optional.ofNullable(p);
    }

}
