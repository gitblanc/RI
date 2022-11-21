/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.util.List;

import uo.ri.conf.Factory;
import uo.ri.cws.application.repository.PayrollRepository;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.util.DtoAssembler;
import uo.ri.cws.application.util.command.Command;

/**
 * @author UO285176
 *
 */
public class GetAllPayrolls implements Command<List<PayrollSummaryBLDto>> {

    private PayrollRepository repo = Factory.repository.forPayroll();

    @Override
    public List<PayrollSummaryBLDto> execute() throws BusinessException {
	return DtoAssembler.toPayrollSummaryDto(repo.findAll());
    }

}
