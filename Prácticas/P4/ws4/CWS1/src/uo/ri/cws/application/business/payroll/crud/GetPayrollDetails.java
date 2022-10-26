/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.util.Optional;

import assertion.Argument;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.payroll.assembler.PayrollAssembler;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;

/**
 * @author UO285176
 *
 */
public class GetPayrollDetails implements Command<Optional<PayrollBLDto>> {

	String id = null;

	public GetPayrollDetails(String id) {
		Argument.isNotNull(id, "The id can't be null");
		Argument.isNotEmpty(id, "The id can't be null");
		this.id = id;
	}

	@Override
	public Optional<PayrollBLDto> execute() throws BusinessException {
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		return PayrollAssembler.toBLDto(pg.findById(id));
	}

}
