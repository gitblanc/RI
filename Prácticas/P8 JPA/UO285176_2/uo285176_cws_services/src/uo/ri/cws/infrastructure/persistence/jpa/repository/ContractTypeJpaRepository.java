/**
 * 
 */
package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import uo.ri.cws.application.repository.ContractTypeRepository;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

/**
 * @author UO285176
 *
 */
public class ContractTypeJpaRepository extends BaseJpaRepository<ContractType>
	implements ContractTypeRepository {

    @Override
    public Optional<ContractType> findByName(String name) {
	TypedQuery<ContractType> tq = Jpa.getManager()
		.createNamedQuery("ContractType.findByName", ContractType.class)
		.setParameter(1, name);

	ContractType ct = null;
	// Obtenemos los resultados NO USAR getSingleResult()
	List<ContractType> result = tq.getResultList();

	if (!result.isEmpty())
	    ct = result.get(0);

	return Optional.ofNullable(ct);
    }

}
