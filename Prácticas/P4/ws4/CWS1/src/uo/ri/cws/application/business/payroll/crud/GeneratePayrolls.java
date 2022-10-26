/**
 * 
 */
package uo.ri.cws.application.business.payroll.crud;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import uo.ri.cws.application.business.BusinessException;
import uo.ri.cws.application.business.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.business.util.command.Command;
import uo.ri.cws.application.persistence.PersistenceFactory;
import uo.ri.cws.application.persistence.contract.ContractGateway;
import uo.ri.cws.application.persistence.contract.ContractGateway.ContractDALDto;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway;
import uo.ri.cws.application.persistence.mechanic.MechanicGateway.MechanicDALDto;
import uo.ri.cws.application.persistence.payroll.PayrollGateway;
import uo.ri.cws.application.persistence.payroll.PayrollGateway.PayrollDALDto;

/**
 * @author UO285176
 *
 */
public class GeneratePayrolls implements Command<PayrollBLDto> {

	LocalDate date = null;

	public GeneratePayrolls(LocalDate d) {
		date = d;
	}

	@Override
	public PayrollBLDto execute() throws BusinessException {

		MechanicGateway mg = PersistenceFactory.forMechanic();
		// Listamos todos los mecánicos
		List<MechanicDALDto> mechanics = mg.findAll();
		// Listamos todos los contratos de los mecánicos
		List<ContractDALDto> contracts = findContracts(mechanics);
		// Listamos las payrolls
		List<PayrollDALDto> payrolls = createPayrolls(contracts);
		generatePayrolls(payrolls);
		return null;
	}

	private void generatePayrolls(List<PayrollDALDto> payrolls) {
		PayrollGateway pg = PersistenceFactory.forPayRoll();
		for (PayrollDALDto p : payrolls) {
			pg.add(p);
		}
	}

	private List<PayrollDALDto> createPayrolls(List<ContractDALDto> contracts) {
		List<PayrollDALDto> payrolls = new ArrayList<>();
		for (ContractDALDto c : contracts) {
			payrolls.add(createOnePayroll(c));
		}
		return payrolls;
	}

	private PayrollDALDto createOnePayroll(ContractDALDto c) {
		PayrollDALDto p = new PayrollDALDto();
		assignValues(c, p);
		return p;
	}

	private void assignValues(ContractDALDto c, PayrollDALDto p) {
		p.id = UUID.randomUUID().toString();
		p.version = 1;
		p.contractId = c.id;
		if (date == null) {
			p.date = LocalDate.now();
		} else {
			p.date = this.date;
		}
		// Earnings
		p.monthlyWage = 0;
		p.bonus = 0;
		p.productivityBonus = 0;
		p.trienniumPayment = 0;
		// Deductions
		p.incomeTax = 0;
		p.nic = 0;
		// Net wage
		p.netWage = 0;
	}

	/*
	 * Busca los contratos asociados a los mecánicos
	 */
	private List<ContractDALDto> findContracts(List<MechanicDALDto> mechanics) {
		List<ContractDALDto> contracts = new ArrayList<>();
		ContractGateway cg = PersistenceFactory.forContract();
		for (MechanicDALDto m : mechanics) {
			Optional<ContractDALDto> contract = cg.findContractInForceById(m.id);
			if (contract != null && !contract.isEmpty())
				contracts.add(contract.get());
		}
		return contracts;
	}

}
