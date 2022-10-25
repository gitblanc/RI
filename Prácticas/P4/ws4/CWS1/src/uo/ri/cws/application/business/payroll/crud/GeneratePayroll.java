/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

/**
 * @author UO285176
 *
 */
public class GeneratePayroll implements Command<PayrollBLDto>{

	@Override
	public PayrollBLDto execute() throws BusinessException {
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		pg.add(null);
		return null;//falta
	}

}
