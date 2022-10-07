package uo.ri.cws.application.ui.cashier.action;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import console.Console;
import math.Round;
import menu.Action;
import uo.ri.cws.application.business.BusinessException;

public class WorkOrdersBillingAction implements Action {
	private static final String URL = "jdbc:hsqldb:hsql://localhost";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	private static final String SQL_CHECK_WORKORDER_STATE = 
			"select state from TWorkOrders where id = ?";

	private static final String SQL_LAST_INVOICE_NUMBER = 
			"select max(number) from TInvoices";

	private static final String SQL_FIND_WORKORDER_AMOUNT = 
			"select amount from TWorkOrders where id = ?";
	
	private static final String SQL_INSERT_INVOICE = 
			"insert into TInvoices(id, number, date, vat, amount, state, version) "
					+ "	values(?, ?, ?, ?, ?, ?, ?)";

	private static final String SQL_LINK_WORKORDER_TO_INVOICE = 
			"update TWorkOrders set invoice_id = ? where id = ?";

	private static final String SQL_MARK_WORKORDER_AS_INVOICED = 
			"update TWorkOrders set state = 'INVOICED' where id = ?";

	private static final String SQL_FIND_WORKORDERS = 
			"select * from TWorkOrders where id = ?";
	
	private static final String SQL_UPDATEVERSION_WORKORDERS = 
			"update TWorkOrders set version=version+1 where id = ?";
	private Connection connection;	

	@Override
	public void execute() throws BusinessException {
		List<String> workOrderIds = new ArrayList<String>();

		// type work order ids to be invoiced in the invoice
		do {
			String id = Console.readString("Type work order ids:  ");
			workOrderIds.add(id);
		} while ( nextWorkorder() );

		try {
			connection = DriverManager.getConnection(URL, USER, PASSWORD);

			if (! checkWorkOrdersExist(workOrderIds) )
				throw new BusinessException ("Workorder does not exist");
			if (! checkWorkOrdersFinished(workOrderIds) )
				throw new BusinessException ("Workorder is not finished yet");

			long numberInvoice = generateInvoiceNumber();
			LocalDate dateInvoice = LocalDate.now();
			double amount = calculateTotalInvoice(workOrderIds); // vat not included
			double vat = vatPercentage(amount, dateInvoice);
			double total = amount * (1 + vat/100); // vat included
			total = Round.twoCents(total);

			String idInvoice = createInvoice(numberInvoice, dateInvoice, vat, total);
			linkWorkordersToInvoice(idInvoice, workOrderIds);
			markWorkOrderAsInvoiced(workOrderIds);
			updateVersion(workOrderIds);
			displayInvoice(numberInvoice, dateInvoice, amount, vat, total);

			connection.commit();
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			if (connection != null) try { connection.close(); } catch(SQLException e) { /* ignore */ }
		}

	}


	private void updateVersion(List<String> workOrderIds) throws SQLException {
		PreparedStatement pst = null;
		
		try {
			pst = connection.prepareStatement(SQL_UPDATEVERSION_WORKORDERS);

			for (String workOrderID : workOrderIds) {
				pst.setString(1, workOrderID);
				pst.executeUpdate();
				}
		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}		
	}


	/*
	 * read work order ids to invoice
	 */
	private boolean nextWorkorder() {
		return Console.readString(" Any other workorder? (y/n) ").equalsIgnoreCase("y");
	}

	/*
	 * checks whether every work order exist	 
	 */
	private boolean checkWorkOrdersExist(List<String> workOrderIDS) throws SQLException, BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		try {
			pst = connection.prepareStatement(SQL_FIND_WORKORDERS);

			for (String workOrderID : workOrderIDS) {
				pst.setString(1, workOrderID);

				rs = pst.executeQuery();
				if (rs.next() == false) {
					return false;
				}

			}
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
		return true;
	}

	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private boolean checkWorkOrdersFinished(List<String> workOrderIDS) throws SQLException, BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(SQL_CHECK_WORKORDER_STATE);

			for (String workOrderID : workOrderIDS) {
				pst.setString(1, workOrderID);

				rs = pst.executeQuery();
				rs.next();
				String state = rs.getString(1); 
				if (! "FINISHED".equalsIgnoreCase(state) ) {
					return false;
				}

			}
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
		return true;
	}

	/*
	 * Generates next invoice number (not to be confused with the inner id)
	 */
	private Long generateInvoiceNumber() throws SQLException {
		PreparedStatement pst = null;
		ResultSet rs = null;

		try {
			pst = connection.prepareStatement(SQL_LAST_INVOICE_NUMBER);
			rs = pst.executeQuery();

			if (rs.next()) {
				return rs.getLong(1) + 1; // +1, next
			} else { // there is none yet
				return 1L;
			}
		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
	}

	/*
	 * Compute total amount of the invoice  (as the total of individual work orders' amount 
	 */
	private double calculateTotalInvoice(List<String> workOrderIDS) throws BusinessException, SQLException {

		double totalInvoice = 0.0;
		for (String workOrderID : workOrderIDS) {
			totalInvoice += getWorkOrderTotal(workOrderID);
		}
		return totalInvoice;
	}

	/*
	 * checks whether every work order id is FINISHED	 
	 */
	private Double getWorkOrderTotal(String workOrderID) throws SQLException, BusinessException {
		PreparedStatement pst = null;
		ResultSet rs = null;
		Double money = 0.0;

		try {
			pst = connection.prepareStatement(SQL_FIND_WORKORDER_AMOUNT);
			pst.setString(1, workOrderID);

			rs = pst.executeQuery();
			if (rs.next() == false) {
				throw new BusinessException("Workorder " + workOrderID + " doesn't exist");
			}

			money = rs.getDouble(1); 

		} finally {
			if (rs != null) try { rs.close(); } catch(SQLException e) { /* ignore */ }
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
		return money;

	}

	/*
	 * returns vat percentage 
	 */
	private double vatPercentage(double totalInvoice, LocalDate dateInvoice) {
		return LocalDate.parse("2012-07-01").isBefore(dateInvoice) ? 21.0 : 18.0;

	}

	/*
	 * Creates the invoice in the database; returns the id
	 */
	private String createInvoice(long numberInvoice, LocalDate dateInvoice, 
			double vat, double total) throws SQLException {

		PreparedStatement pst = null;
		String idInvoice;

		try {
			idInvoice = UUID.randomUUID().toString();
			
			pst = connection.prepareStatement(SQL_INSERT_INVOICE);
			pst.setString(1, idInvoice);
			pst.setLong(2, numberInvoice);
			pst.setDate(3, java.sql.Date.valueOf(dateInvoice));
			pst.setDouble(4, vat);
			pst.setDouble(5, total);
			pst.setString(6, "NOT_YET_PAID");
			pst.setLong(7, 1L);

			pst.executeUpdate();

		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
		return idInvoice; 
	}

	/*
	 * Set the invoice number field in work order table to the invoice number generated
	 */
	private void linkWorkordersToInvoice (String invoiceId, List<String> workOrderIDS) throws SQLException {

		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(SQL_LINK_WORKORDER_TO_INVOICE);

			for (String workOrderId : workOrderIDS) {
				pst.setString(1, invoiceId);
				pst.setString(2, workOrderId);

				pst.executeUpdate();
			}
		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
	}




	/*
	 * Sets state to INVOICED for every workorder
	 */
	private void markWorkOrderAsInvoiced(List<String> ids) throws SQLException {

		PreparedStatement pst = null;
		try {
			pst = connection.prepareStatement(SQL_MARK_WORKORDER_AS_INVOICED);

			for (String id: ids) {
				pst.setString(1, id);

				pst.executeUpdate();
			}
		} finally {
			if (pst != null) try { pst.close(); } catch(SQLException e) { /* ignore */ }
		}
	}


	private void displayInvoice(long numberInvoice, LocalDate dateInvoice,
			double totalInvoice, double vat, double totalConIva) {

		Console.printf("Invoice number: %d\n", numberInvoice);
		Console.printf("\tDate: %1$td/%1$tm/%1$tY\n", dateInvoice);
		Console.printf("\tAmount: %.2f €\n", totalInvoice);
		Console.printf("\tVAT: %.1f %% \n", vat);
		Console.printf("\tTotal (including VAT): %.2f €\n", totalConIva);
	}

}
