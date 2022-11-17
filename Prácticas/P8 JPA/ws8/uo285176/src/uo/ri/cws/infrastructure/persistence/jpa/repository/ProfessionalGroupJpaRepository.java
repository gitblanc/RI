/**
 * 
 */
package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.TypedQuery;

import uo.ri.cws.application.repository.ProfessionalGroupRepository;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

/**
 * @author UO285176
 *
 */
public class ProfessionalGroupJpaRepository
	extends BaseJpaRepository<ProfessionalGroup>
	implements ProfessionalGroupRepository {

    @Override
    public Optional<ProfessionalGroup> findByName(String name) {
	TypedQuery<ProfessionalGroup> tq = Jpa.getManager()
		.createNamedQuery("ProfessionalGroup.findByName",
			ProfessionalGroup.class)
		.setParameter(1, name);

	ProfessionalGroup p = null;
	// Obtenemos los resultados NO USAR getSingleResult()
	List<ProfessionalGroup> result = tq.getResultList();

	if (!result.isEmpty())
	    p = result.get(0);

	return Optional.ofNullable(p);
    }

}
