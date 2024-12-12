package com.demo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.demo.entities.Account;
import com.demo.entities.Branch;
import com.demo.entities.ConnectDB;

public class BranchModel {
	public Branch findBranchByID(int branchid) {
		Branch branch = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from branch where id = ?");
			preparedStatement.setInt(1, branchid);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				branch = new Branch();
				branch.setId(resultSet.getInt("id"));
				branch.setName(resultSet.getString("name"));
				branch.setAddress(resultSet.getString("address"));
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			branch = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		return branch;
	}
	public static void main(String[] args) {
		BranchModel branchModel = new BranchModel();
		System.out.println(branchModel.findBranchByID(1));
	}
}
