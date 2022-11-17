/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;
import uo.ri.util.assertion.ArgumentChecks;

/**
 * @author UO285176
 *
 */
public class GetPayrollDetails implements Command<Optional<PayrollBLDto>> {

    private String id = null;
    private PayrollRepository repo = Factory.repository.forPayroll();

    public GetPayrollDetails(String id) {
	ArgumentChecks.isNotNull(id, "The mechanic id can't be null");
	ArgumentChecks.isNotEmpty(id, "The mechanic id can't be empty");
	ArgumentChecks.isNotBlank(id, "The id can't be blank");
	this.id = id;
    }

    @Override
    public Optional<PayrollBLDto> execute() throws BusinessException {
	return DtoAssembler.toPayrollDto(repo.findById(id));
    }

}
