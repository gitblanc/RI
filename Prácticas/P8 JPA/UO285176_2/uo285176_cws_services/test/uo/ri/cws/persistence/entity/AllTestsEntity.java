package uo.ri.cws.persistence.entity;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ChargeMappingTests.class, ClientMappingTests.class,
	CreditCardMappingTests.class, InterventionMappingTests.class,
	InvoiceMappingTests.class, MechanicMappingTests.class,
	SparePartMappingTests.class, SubstitutionMappingTests.class,
	VehicleMappingTests.class, VehicleTypeMappingTests.class,
	VoucherMappingTests.class, WorkOrderMappingTests.class })
public class AllTestsEntity {

}
