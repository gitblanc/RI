/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.util.command.Command;

/**
 * @author UO285176
 *
 */
public class GeneratePayroll implements Command<PayrollBLDto>{

	@Override
	public PayrollBLDto execute() throws BusinessException {
		return null;
	}

}
