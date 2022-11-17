/**
 * 
 */
package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.repository.ContractRepository;
import uo.ri.cws.domain.Contract;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;
import uo.ri.util.exception.NotYetImplementedException;

/**
 * @author UO285176
 *
 */
public class ContractJpaRepository extends BaseJpaRepository<Contract>
	implements ContractRepository {

    @Override
    public List<Contract> findAllInForce() {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public List<Contract> findByMechanicId(String mechanic_id) {
	return Jpa.getManager()
		.createNamedQuery("Contract.findByMechanicId", Contract.class)
		.setParameter(1, mechanic_id).getResultList();
    }

    @Override
    public List<Contract> findByProfessionalGroupId(String group_id) {
	return Jpa.getManager()
		.createNamedQuery("Contract.findByProfessionalGroupId",
			Contract.class)
		.setParameter(1, group_id).getResultList();
    }

    @Override
    public List<Contract> findByContractTypeId(String id2Del) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public List<Contract> findAllInForceThisMonth(LocalDate present) {
	throw new NotYetImplementedException("SIN HACER");
    }

}
