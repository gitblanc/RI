package uo.ri.cws.infrastructure.persistence.jpa.repository;

import java.util.List;

import uo.ri.cws.application.repository.WorkOrderRepository;
import uo.ri.cws.domain.WorkOrder;
import uo.ri.cws.infrastructure.persistence.jpa.util.BaseJpaRepository;
import uo.ri.cws.infrastructure.persistence.jpa.util.Jpa;
import uo.ri.util.exception.NotYetImplementedException;

public class WorkOrderJpaRepository extends BaseJpaRepository<WorkOrder>
	implements WorkOrderRepository {

    @Override
    public List<WorkOrder> findByIds(List<String> idsAveria) {
	return Jpa.getManager()
		.createNamedQuery("WorkOrder.findByIds", WorkOrder.class)
		.setParameter(1, idsAveria).getResultList();
    }

    @Override
    public List<WorkOrder> findNotInvoicedWorkOrdersByClientDni(String dni) {
	throw new NotYetImplementedException("SIN HACER");
    }

    @Override
    public List<WorkOrder> findByMechanic(String mechanic_id) {
	return Jpa.getManager()
		.createNamedQuery("WorkOrder.findByMechanicId", WorkOrder.class)
		.setParameter(1, mechanic_id).getResultList();
    }

}
