/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService;
import uo.ri.cws.application.business.util.command.CommandExecutor;

/**
 * @author UO285176
 *
 */
public class PayrollServiceImpl implements PayrollService {

	CommandExecutor executor = new CommandExecutor();

	@Override
	public void generatePayrolls() throws BusinessException {
		executor.execute(new GeneratePayrolls(null));
	}

	@Override
	public void generatePayrolls(LocalDate present) throws BusinessException {
		executor.execute(new GeneratePayrolls(present));
	}

	@Override
	public void deleteLastPayrollFor(String mechanicId)
			throws BusinessException {
		executor.execute(new DeleteLastPayrollForMechanic(mechanicId));

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
