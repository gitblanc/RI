package uo.ri.cws.persistence.association;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AssignMappingTests.class, ChargeMappingTests.class, ClassifyMappingTests.class,
		InterveneMappingTests.class, InvoiceMappingTests.class, OrderMappingTests.class, OwnMappingTests.class,
		SubstituteMappingTests.class })
public class AllTests {

}
