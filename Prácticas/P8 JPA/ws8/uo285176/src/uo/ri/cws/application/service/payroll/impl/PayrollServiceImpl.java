/**
 * 
 */
package uo.ri.cws.application.service.payroll.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.conf.Factory;
import uo.ri.cws.application.service.BusinessException;
import uo.ri.cws.application.service.payroll.PayrollService;
import uo.ri.cws.application.util.command.CommandExecutor;

/**
 * @author UO285176
 *
 */
public class PayrollServiceImpl implements PayrollService {

    private CommandExecutor executor = Factory.executor.forExecutor();

    @Override
    public void generatePayrolls() throws BusinessException {
	executor.execute(new GeneratePayrolls());
    }

    @Override
    public void generatePayrolls(LocalDate present) throws BusinessException {
	executor.execute(new GeneratePayrolls(present));
    }

    @Override
    public void deleteLastPayrollFor(String mechanicId)
	    throws BusinessException {
	executor.execute(new DeleteLastPayrollsFor(mechanicId));
    }

    @Override
    public void deleteLastPayrolls() throws BusinessException {
	executor.execute(new DeleteLastPayrolls());
    }

    @Override
    public Optional<PayrollBLDto> getPayrollDetails(String id)
	    throws BusinessException {
	return executor.execute(new GetPayrollDetails(id));
    }

    @Override
    public List<PayrollSummaryBLDto> getAllPayrolls() throws BusinessException {
	return executor.execute(new GetAllPayrolls());
    }

    @Override
    public List<PayrollSummaryBLDto> getAllPayrollsForMechanic(String id)
	    throws BusinessException {
	return executor.execute(new GetAllPayrollsForMechanic(id));
    }

    @Override
    public List<PayrollSummaryBLDto> getAllPayrollsForProfessionalGroup(
	    String name) throws BusinessException {
	return executor.execute(new GetAllPayrollsForProfessionalGroup(name));
    }

}
