package uo.ri.cws.application.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import uo.ri.cws.application.service.client.ClientCrudService.ClientDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CardDto;
import uo.ri.cws.application.service.invoice.InvoicingService.CashDto;
import uo.ri.cws.application.service.invoice.InvoicingService.InvoiceDto;
import uo.ri.cws.application.service.invoice.InvoicingService.PaymentMeanDto;
import uo.ri.cws.application.service.invoice.InvoicingService.VoucherDto;
import uo.ri.cws.application.service.mechanic.MechanicCrudService.MechanicDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollBLDto;
import uo.ri.cws.application.service.payroll.PayrollService.PayrollSummaryBLDto;
import uo.ri.cws.application.service.professionalgroup.ProfessionalGroupService.ProfessionalGroupBLDto;
import uo.ri.cws.application.service.vehicle.VehicleCrudService.VehicleDto;
import uo.ri.cws.application.service.vehicleType.VehicleTypeCrudService.VehicleTypeDto;
import uo.ri.cws.application.service.workorder.WorkOrderCrudService.WorkOrderDto;
import uo.ri.cws.domain.Cash;
import uo.ri.cws.domain.Client;
import uo.ri.cws.domain.CreditCard;
import uo.ri.cws.domain.Invoice;
import uo.ri.cws.domain.Mechanic;
import uo.ri.cws.domain.PaymentMean;
import uo.ri.cws.domain.Payroll;
import uo.ri.cws.domain.ProfessionalGroup;
import uo.ri.cws.domain.Vehicle;
import uo.ri.cws.domain.VehicleType;
import uo.ri.cws.domain.Voucher;
import uo.ri.cws.domain.WorkOrder;

public class DtoAssembler {

    public static ClientDto toDto(Client c) {
	ClientDto dto = new ClientDto();

	dto.id = c.getId();
	dto.version = c.getVersion();

	dto.dni = c.getDni();
	dto.name = c.getName();
	dto.surname = c.getSurname();

	return dto;
    }

    public static List<ClientDto> toClientDtoList(List<Client> clientes) {
	List<ClientDto> res = new ArrayList<>();
	for (Client c : clientes) {
	    res.add(DtoAssembler.toDto(c));
	}
	return res;
    }

    public static MechanicDto toDto(Mechanic m) {
	MechanicDto dto = new MechanicDto();
	dto.id = m.getId();
	dto.version = m.getVersion();

	dto.dni = m.getDni();
	dto.name = m.getName();
	dto.surname = m.getSurname();
	return dto;
    }

    public static List<MechanicDto> toMechanicDtoList(List<Mechanic> list) {
	List<MechanicDto> res = new ArrayList<>();
	for (Mechanic m : list) {
	    res.add(toDto(m));
	}
	return res;
    }

    public static List<VoucherDto> toVoucherDtoList(List<Voucher> list) {
	List<VoucherDto> res = new ArrayList<>();
	for (Voucher b : list) {
	    res.add(toDto(b));
	}
	return res;
    }

    public static VoucherDto toDto(Voucher v) {
	VoucherDto dto = new VoucherDto();
	dto.id = v.getId();
	dto.version = v.getVersion();

	dto.clientId = v.getClient().getId();
	dto.accumulated = v.getAccumulated();
	dto.code = v.getCode();
	dto.description = v.getDescription();
	dto.available = v.getAvailable();
	return dto;
    }

    public static CardDto toDto(CreditCard cc) {
	CardDto dto = new CardDto();
	dto.id = cc.getId();
	dto.version = cc.getVersion();

	dto.clientId = cc.getClient().getId();
	dto.accumulated = cc.getAccumulated();
	dto.cardNumber = cc.getNumber();
	dto.cardExpiration = cc.getValidThru();
	dto.cardType = cc.getType();
	return dto;
    }

    public static CashDto toDto(Cash m) {
	CashDto dto = new CashDto();
	dto.id = m.getId();
	dto.version = m.getVersion();

	dto.clientId = m.getClient().getId();
	dto.accumulated = m.getAccumulated();
	return dto;
    }

    public static InvoiceDto toDto(Invoice invoice) {
	InvoiceDto dto = new InvoiceDto();
	dto.id = invoice.getId();
	dto.version = invoice.getVersion();

	dto.number = invoice.getNumber();
	dto.date = invoice.getDate();
	dto.total = invoice.getAmount();
	dto.vat = invoice.getVat();
	dto.state = invoice.getState().toString();
	return dto;
    }

    public static List<PaymentMeanDto> toPaymentMeanDtoList(
	    List<PaymentMean> list) {
	return list.stream().map(mp -> toDto(mp)).collect(Collectors.toList());
    }

    private static PaymentMeanDto toDto(PaymentMean mp) {
	if (mp instanceof Voucher) {
	    return toDto((Voucher) mp);
	} else if (mp instanceof CreditCard) {
	    return toDto((CreditCard) mp);
	} else if (mp instanceof Cash) {
	    return toDto((Cash) mp);
	} else {
	    throw new RuntimeException("Unexpected type of payment mean");
	}
    }

    public static WorkOrderDto toDto(WorkOrder a) {
	WorkOrderDto dto = new WorkOrderDto();
	dto.id = a.getId();
	dto.version = a.getVersion();

	dto.vehicleId = a.getVehicle().getId();
	dto.description = a.getDescription();
	dto.date = a.getDate();
	dto.total = a.getAmount();
	dto.state = a.getStatus().toString();

	dto.invoiceId = a.getInvoice() == null ? null : a.getInvoice().getId();

	return dto;
    }

    public static VehicleDto toDto(Vehicle v) {
	VehicleDto dto = new VehicleDto();
	dto.id = v.getId();
	dto.version = v.getVersion();

	dto.plate = v.getPlateNumber();
	dto.clientId = v.getClient().getId();
	dto.make = v.getMake();
	dto.vehicleTypeId = v.getVehicleType().getId();
	dto.model = v.getModel();

	return dto;
    }

    public static List<WorkOrderDto> toWorkOrderDtoList(List<WorkOrder> list) {
	return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
    }

    public static VehicleTypeDto toDto(VehicleType vt) {
	VehicleTypeDto dto = new VehicleTypeDto();

	dto.id = vt.getId();
	dto.version = vt.getVersion();

	dto.name = vt.getName();
	dto.pricePerHour = vt.getPricePerHour();

	return dto;
    }

    public static List<VehicleTypeDto> toVehicleTypeDtoList(
	    List<VehicleType> list) {
	return list.stream().map(a -> toDto(a)).collect(Collectors.toList());
    }

    public static ProfessionalGroupBLDto toProfessionalGroupBLDto(
	    ProfessionalGroup p) {
	ProfessionalGroupBLDto dto = new ProfessionalGroupBLDto();
	dto.id = p.getId();
	dto.name = p.getName();
	dto.productivityRate = p.getProductivityBonusPercentage();
	dto.trieniumSalary = p.getTrienniumPayment();
	dto.version = p.getVersion();

	return dto;
    }

    public static Optional<ProfessionalGroupBLDto> toProfessionalGroupDto(
	    Optional<ProfessionalGroup> arg) {
	Optional<ProfessionalGroupBLDto> result = arg.isEmpty()
		? Optional.ofNullable(null)
		: Optional.ofNullable(toProfessionalGroupBLDto(arg.get()));
	return result;
    }

    public static List<ProfessionalGroupBLDto> toProfessionalGroupBLDtoList(
	    List<ProfessionalGroup> arg) {
	List<ProfessionalGroupBLDto> result = new ArrayList<ProfessionalGroupBLDto>();
	for (ProfessionalGroup mr : arg)
	    result.add(toProfessionalGroupBLDto(mr));
	return result;
    }

    public static List<PayrollSummaryBLDto> toPayrollSummaryDto(
	    List<Payroll> arg) {
	List<PayrollSummaryBLDto> result = new ArrayList<PayrollSummaryBLDto>();
	for (Payroll mr : arg)
	    result.add(toPayrollSummaryBLDto(mr));
	return result;
    }

    private static PayrollSummaryBLDto toPayrollSummaryBLDto(Payroll rs) {
	PayrollSummaryBLDto p = new PayrollSummaryBLDto();
	p.id = rs.getId();
	p.version = rs.getVersion();
	p.date = rs.getDate();
	p.netWage = calculateNetWage(rs);
	return p;
    }

    private static double calculateNetWage(Payroll p) {
	// Abonos -> monthlyWage + bonus productivityBonus + trienniumpayment
	double salary = p.getMonthlyWage();
	double bonus = p.getBonus();
	double pBonus = p.getProductivityBonus();
	double trienn = p.getTrienniumPayment();

	return salary + bonus + pBonus + trienn - p.getIncomeTax() - p.getNIC();
    }

    public static Optional<PayrollBLDto> toPayrollDto(Optional<Payroll> arg) {
	Optional<PayrollBLDto> result = (arg.isEmpty() || arg == null)
		? Optional.ofNullable(null)
		: Optional.ofNullable(toPayrollBLDto(arg.get()));
	return result;
    }

    private static PayrollBLDto toPayrollBLDto(Payroll x) {
	PayrollBLDto payroll = new PayrollBLDto();
	payroll.id = x.getId();
	payroll.bonus = x.getBonus();
	payroll.date = x.getDate();
	payroll.incomeTax = x.getIncomeTax();
	payroll.monthlyWage = x.getMonthlyWage();
	payroll.nic = x.getNIC();
	payroll.productivityBonus = x.getProductivityBonus();
	payroll.trienniumPayment = x.getTrienniumPayment();
	payroll.version = x.getVersion();
	payroll.contractId = x.getContract().getId();
	payroll.netWage = calculateNetWage(x);
	return payroll;
    }

}
