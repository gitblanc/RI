package uo.ri.cws.domain;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CashTest.class, ChargeTests.class, CreditCardTest.class, InterventionTest.class, InvoiceTests.class,
		SubstitutionTests.class, VoucherTest.class, WorkOrderTests.class })
public class AllTests {

}
