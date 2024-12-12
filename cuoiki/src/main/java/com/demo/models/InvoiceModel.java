package com.demo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.demo.entities.ConnectDB;
import com.demo.entities.Invoice;

public class InvoiceModel {
	public List<Invoice> findAll(){
		List<Invoice> invoices = new ArrayList<Invoice>();
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from invoice");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Invoice invoice = new Invoice();
				invoice.setId(resultSet.getInt("id"));
				invoice.setAccountId(resultSet.getInt("accountID"));
				invoice.setServiceId(resultSet.getInt("serviceID"));
				invoice.setDescription(resultSet.getString("description"));
				invoice.setCreated(resultSet.getDate("created"));
				invoice.setStatus(resultSet.getBoolean("status"));
				
				invoices.add(invoice);
			}
		} catch (Exception e) {
			e.printStackTrace();
			invoices = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		
		return invoices;
	}
	
	public Invoice findByID(int id, boolean status){
		Invoice invoice = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from invoice where accountID = ? AND status = true");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				invoice = new Invoice();
				invoice.setId(resultSet.getInt("id"));
				invoice.setAccountId(resultSet.getInt("accountID"));
				invoice.setServiceId(resultSet.getInt("serviceID"));
				invoice.setDescription(resultSet.getString("description"));
				invoice.setCreated(resultSet.getDate("created"));
				invoice.setStatus(resultSet.getBoolean("status"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			invoice = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		
		return invoice;
	}
	
	public boolean register(Invoice invoice) {
		boolean status = true;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
			.prepareStatement("insert into invoice(accountID, serviceID, durationID, description, created, status) values(?, ?, ?, ?, ?, ?)");
			preparedStatement.setInt(1, invoice.getAccountId());
			preparedStatement.setInt(2, invoice.getServiceId());
			preparedStatement.setInt(3, invoice.getDurationId());
			preparedStatement.setString(4, invoice.getDescription());
			preparedStatement.setTimestamp(5, new Timestamp(invoice.getCreated().getTime()));
			preparedStatement.setBoolean(6, invoice.isStatus());
			
			status = preparedStatement.executeUpdate() > 0;
			
			
		} catch (Exception e) {
			e.printStackTrace();
			status = false;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		return status;
	}
	
	public static void main(String[] args) {
		InvoiceModel invoiceModel = new InvoiceModel();
		System.out.println(invoiceModel.findByID(2, true));
	}
}
