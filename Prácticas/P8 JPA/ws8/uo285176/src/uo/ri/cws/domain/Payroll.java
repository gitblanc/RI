/**
 * 
 */
package uo.ri.cws.domain;

import java.time.LocalDate;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import uo.ri.cws.domain.base.BaseEntity;

/**
 * @author UO285176
 *
 */
@Entity
@Table(name = "tpayrolls")
public class Payroll extends BaseEntity{
	
	@Column(unique = true)
	public String id;
	@Basic(optional = false)
	public long version;

	@Basic(optional = false)
	public String contractId;
	@Basic(optional = false)
	public LocalDate date;

	// Earnings
	@Basic(optional = false)
	public double monthlyWage;
	@Basic(optional = false)
	public double bonus;
	@Basic(optional = false)
	public double productivityBonus;
	@Basic(optional = false)
	public double trienniumPayment;

	// Deductions
	@Basic(optional = false)
	public double incomeTax;
	@Basic(optional = false)
	public double nic;

	// Net wage
	@Basic(optional = false)
	public double netWage;
}
