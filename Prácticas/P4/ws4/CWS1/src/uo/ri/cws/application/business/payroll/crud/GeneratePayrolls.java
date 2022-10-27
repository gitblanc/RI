/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import math.Round;
import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;
import uo.ri.cws.application.persistence.professionalgroup.ProfessionalGroupGateway.ProfessionalGroupDALDto;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway;
import uo.ri.cws.application.persistence.workorder.WorkOrderGateway.WorkOrderDALDto;

/**
 * @author UO285176
 *
 */
public class GeneratePayrolls implements Command<PayrollBLDto> {

	LocalDate date = null;

	public GeneratePayrolls() {
		this(null);
	}

	public GeneratePayrolls(LocalDate d) {
		if (d == null)
			this.date = LocalDate.now();
		else
			date = d;
	}

	@Override
	public PayrollBLDto execute() throws BusinessException {
		ContractGateway cg = PersistenceFactory.forContract();

		// Listamos todos los contratos de los mecánicos en vigor
		List<ContractDALDto> contracts = cg.findContractsInForce();
		if (!contracts.isEmpty()) {
			// Listamos las payrolls
			PayrollGateway pg = PersistenceFactory.forPayRoll();
			for (ContractDALDto c : contracts) {
				List<PayrollDALDto> payrolls = pg.findByContractId(c.id);

				// Si el contrato no tiene nóminas asignadas
				if (payrolls.isEmpty()) {
					generateNewPayroll(c);
				}
			}
		}
		return null;
	}

	private void generateNewPayroll(ContractDALDto c) throws BusinessException {
		PayrollDALDto p = new PayrollDALDto();
		p.id = UUID.randomUUID().toString();
		if (date == null)
			p.date = LocalDate.now();
		else
			p.date = this.date;
		p.version = 1L;
		p.contractId = c.id;

		// Abonos
		p.monthlyWage = c.annualBaseWage / 14;// salario base mensual
		// paga extra(bonus en junio y diciembre)
		if (p.date.getMonth().equals(Month.JUNE)
				|| p.date.getMonth().equals(Month.DECEMBER)) {
			p.bonus = c.annualBaseWage / 14;
		} else {
			p.bonus = 0;
		}
		p.trienniumPayment = calculateTrienniumPayment(c);// triennium
		p.productivityBonus = calculateProductivityBonus(c.dni,
				c.professionalGroupName);// plus de productividad
		double grossWage = p.monthlyWage + p.bonus + p.productivityBonus
				+ p.trienniumPayment;// total bruto

		// Descuentos
		p.incomeTax = calculateIncomeTax(grossWage, c.annualBaseWage);// IRPF
		p.nic = calculateNic(c.annualBaseWage);
		// Finalmente generamos la payroll
		PersistenceFactory.forPayRoll().add(p);
	}

	private double calculateNic(double annualBaseWage) {
		double percentage = 0.05 * annualBaseWage;
		return percentage / 12;
	}

	private double calculateIncomeTax(double grossWage, double annualBaseWage) {
		double res = 0.0;
		if (annualBaseWage <= 12450.0) {
			res = 0.19;
		} else if (annualBaseWage <= 20200.0) {
			res = 0.24;
		} else if (annualBaseWage <= 35200.0) {
			res = 0.3;
		} else if (annualBaseWage <= 60000.0) {
			res = 0.37;
		} else if (annualBaseWage <= 300000.0) {
			res = 0.45;
		} else {
			res = 0.47;
		}
		return res;
	}

	private double calculateTrienniumPayment(ContractDALDto c) {
		double tPayment = 0;

		// obtenemos la antiguedad del contrato
		long time = Period.between(c.startDate, this.date).getYears();
		int numTrienniums = (int) (time / 3);

		Optional<ProfessionalGroupDALDto> group = PersistenceFactory
				.forProfessionalGroup().findById(c.professionalGroupName);
		if (!group.isEmpty()) {
			double triennium = group.get().trieniumSalary;
			tPayment = triennium * numTrienniums;
		}
		return Round.twoCents(tPayment);
	}

	private double calculateProductivityBonus(String idM,
			String professionalGroupId) throws BusinessException {
		WorkOrderGateway wg = PersistenceFactory.forWorkOrder();
		List<WorkOrderDALDto> workOrders = wg.findByMechanic(idM);
		return calculateProductivityBonus(professionalGroupId, workOrders);
	}

	private double calculateProductivityBonus(String professionalGroupId,
			List<WorkOrderDALDto> workOrders) {
		double amount = calculateAmount(workOrders);
		double percentage = obtainPercentage(professionalGroupId);
		return Round.twoCents((amount * percentage) / 100);
	}

	private double obtainPercentage(String professionalGroupId) {
		double porcentaje = 0;
		// Obtenemos el porcentaje en función del grupo profesional y lo
		// aplicamos al amount
		Optional<ProfessionalGroupDALDto> group = PersistenceFactory
				.forProfessionalGroup().findById(professionalGroupId);
		if (!group.isEmpty()) {
			porcentaje = group.get().productivityRate;
		}
		return porcentaje;
	}

	private double calculateAmount(List<WorkOrderDALDto> workOrders) {
		double amount = 0;
		for (WorkOrderDALDto w : workOrders) {
			boolean condDate = w.date.getMonth().equals(this.date.getMonth())
					&& w.date.getYear() == this.date.getYear();
			boolean condState = w.state.equals("INVOICED");

			if (condDate && condState)
				amount += w.amount;
		}

		return amount;
	}

}
