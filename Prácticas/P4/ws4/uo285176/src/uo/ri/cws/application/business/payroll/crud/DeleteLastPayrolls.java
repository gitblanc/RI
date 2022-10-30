/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.time.LocalDate;
import java.util.List;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

/**
 * @author UO285176
 *
 */
public class DeleteLastPayrolls implements Command<PayrollBLDto> {

	@Override
	public PayrollBLDto execute() throws BusinessException {
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		List<PayrollDALDto> payrolls = pg.findAll();
		for (PayrollDALDto p : payrolls) {
			// Comprobamos que la payroll sea del último mes
			if (checkLastMonthPayroll(p)) {
				// Vamos eliminando cada payroll del último mes
				pg.remove(p.id);
			}
		}
		return null;
	}

	/*
	 * Método que comprueba que el mes de la payroll sea el último
	 */
	private boolean checkLastMonthPayroll(PayrollDALDto p) {
		return p.date.getMonthValue() == LocalDate.now().getMonthValue()
				&& p.date.getYear() == LocalDate.now().getYear();
	}

}
