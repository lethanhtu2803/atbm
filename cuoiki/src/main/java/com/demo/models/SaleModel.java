package com.demo.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.demo.entities.Sale;
import com.demo.entities.ConnectDB;

public class SaleModel {
	public List<Sale> findAll(){
		List<Sale> sales = new ArrayList<Sale>();
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from sale where status = true");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Sale sale = new Sale();
				sale.setId(resultSet.getInt("id"));
				sale.setName(resultSet.getString("name"));
				sale.setSaleNumber(resultSet.getDouble("saleNumber"));
				sale.setStatus(resultSet.getBoolean("status"));
				sales.add(sale);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sales = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		
		return sales;
	}
	
	public Sale findSaleById(int id) {
		Sale sale = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from sale where id = ?");
			preparedStatement.setInt(1, id);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				sale = new Sale();
				sale.setId(resultSet.getInt("id"));
				sale.setName(resultSet.getString("name"));
				sale.setSaleNumber(resultSet.getDouble("saleNumber"));
				sale.setStatus(resultSet.getBoolean("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			sale = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		return sale;
	}
	public List<Sale> findSaleByName(String name) {
		List<Sale> sales = new ArrayList<Sale>();
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from sale where name like ?");
			preparedStatement.setString(1,"%" + name + "%");
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				Sale sale = new Sale();
				sale.setId(resultSet.getInt("id"));
				sale.setName(resultSet.getString("name"));
				sale.setSaleNumber(resultSet.getDouble("saleNumber"));
				sale.setStatus(resultSet.getBoolean("status"));
				sales.add(sale);
			}
		} catch (Exception e) {
			e.printStackTrace();
			sales = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		return sales;
	}
	
	public Sale findByName(String name) {
		Sale sale = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from sale where name = ?");
			preparedStatement.setString(1, name);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				sale = new Sale();
				sale.setId(resultSet.getInt("id"));
				sale.setName(resultSet.getString("name"));
				sale.setSaleNumber(resultSet.getDouble("saleNumber"));
				sale.setStatus(resultSet.getBoolean("status"));
			}
		} catch (Exception e) {
			e.printStackTrace();
			sale = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		return sale;
	}
	
	public boolean create(Sale sale) {
		boolean status = true;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
			.prepareStatement("insert into sale(name, saleNumber, status) values(?, ?, ?)");
			preparedStatement.setString(1, sale.getName());
			preparedStatement.setDouble(2, sale.getSaleNumber());
			preparedStatement.setBoolean(3, sale.isStatus());
			
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
	
	public boolean update(Sale sale) {
		boolean status = true;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
			.prepareStatement("update sale set name = ?, saleNumber = ?, status = ? where id = ?");
			preparedStatement.setString(1, sale.getName());
			preparedStatement.setDouble(2, sale.getSaleNumber());
			preparedStatement.setBoolean(3, sale.isStatus());
			preparedStatement.setInt(4, sale.getId());
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
		SaleModel saleModel = new SaleModel();
		Sale sale = new Sale();
		sale.setName("aaa");
		sale.setSaleNumber(20000);
		sale.setStatus(true);
		System.out.println(saleModel.create(sale));
	}

}
