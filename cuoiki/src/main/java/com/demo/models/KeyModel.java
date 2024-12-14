package com.demo.models;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.demo.entities.ConnectDB;
import com.demo.entities.Contact;
import com.demo.entities.Duration;
import com.demo.entities.Feedback;
import com.demo.entities.Key;

public class KeyModel {
	public boolean create(Key key) {
		System.out.println(key);
		boolean status = true;

		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("insert into `key`(userID,publicKey,startTime,endTime) values(?, ?, ?, ?)");
			preparedStatement.setInt(1, key.getUserID());
			preparedStatement.setString(2, key.getPublicKey());

			// Chuyển startTime và endTime từ java.util.Date thành java.sql.Timestamp
			Timestamp startTimestamp = new Timestamp(key.getStartTime().getTime());
			

			// Đặt giá trị cho startTime và endTime
			preparedStatement.setTimestamp(3, startTimestamp);
			if (key.getEndTime() == null) {
				key.setEndTime(new java.util.Date());		
				Timestamp endTimestamp = new Timestamp(key.getEndTime().getTime());
				preparedStatement.setTimestamp(4, null);
			} else {
				Timestamp endTimestamp = new Timestamp(key.getEndTime().getTime());
				preparedStatement.setTimestamp(4, endTimestamp);
			}
			

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
	
	public Key findByAccountID(int userID){
		Key key = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from `key` where userID = ?");
			preparedStatement.setInt(1, userID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while(resultSet.next()) {
				key = new Key();
				key.setId(resultSet.getInt("id"));
				key.setUserID(resultSet.getInt("userID"));
				key.setPublicKey(resultSet.getString("publicKey"));
				key.setStartTime(resultSet.getDate("startTime"));
				key.setEndTime(resultSet.getDate("endTime"));

			}
		} catch (Exception e) {
			e.printStackTrace();
			key = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
		
		return key;
	}
	public static void main(String[] args) {
		System.out.println(new KeyModel().findByAccountID(1));
	}
}
