package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import uo.ri.cws.application.repository.MechanicRepository;
import uo.ri.cws.domain.ContractType;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;

public class MechanicJpaRepository extends BaseJpaRepository<Mechanic>// implementaciones
								      // genéricas
	implements MechanicRepository {

    @Override
    public Optional<Mechanic> findByDni(String dni) {
	EntityManager em = Jpa.getManager();
	// Comprobar que no hay un mecanico con el mismo dni
	TypedQuery<Mechanic> tq = em
		.createNamedQuery("Mechanic.findByDni", Mechanic.class)
		.setParameter(1, dni);

	Mechanic m = null;
	// Obtenemos los resultados NO USAR getSingleResult()
	List<Mechanic> result = tq.getResultList();

	if (!result.isEmpty())
	    m = result.get(0);

	return Optional.ofNullable(m);
    }

    @Override
    public List<Mechanic> findAllInForce() {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Mechanic> findInForceInContractType(ContractType contractType) {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public List<Mechanic> findAllInProfessionalGroup(ProfessionalGroup group) {
	// TODO Auto-generated method stub
	return null;
    }

}
