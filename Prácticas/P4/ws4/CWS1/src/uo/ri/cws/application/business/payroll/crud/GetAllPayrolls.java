/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

/**
 * @author UO285176
 *
 */
public class GetAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

	@Override
	public List<PayrollSummaryBLDto> execute() throws BusinessException {
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		return PayrollAssembler.toBLDtoList(pg.findAllSummary());
	}

}
