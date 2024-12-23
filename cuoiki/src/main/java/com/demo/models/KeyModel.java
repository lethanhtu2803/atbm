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
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement(
					"insert into `key`(userID,publicKey,startTime,endTime, status) values(?, ?, ?, ?, ?)");
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
			preparedStatement.setBoolean(5, key.isStatus());

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

	public Key findByAccountID(int userID) {
		Key key = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("select * from `key` where userID = ? and status = true");
			preparedStatement.setInt(1, userID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
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

	public boolean deleteKeyByID(int keyID) {
		boolean isDeleted = false;
		try {
			// Chuẩn bị câu lệnh SQL để xóa một bản ghi dựa trên keyID
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("DELETE FROM `key` WHERE id = ?");
			preparedStatement.setInt(1, keyID);

			// Thực thi câu lệnh và kiểm tra số lượng bản ghi bị xóa
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				isDeleted = true; // Đánh dấu là đã xóa thành công
			}
		} catch (Exception e) {
			e.printStackTrace();
			isDeleted = false;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}

		return isDeleted;
	}

	public boolean updateKey(Key key) {
		boolean isUpdated = false;
		try {
			// Chuẩn bị câu lệnh SQL để cập nhật bản ghi dựa trên keyID
			PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement(
					"UPDATE `key` SET userID = ?, publicKey = ?, startTime = ?, endTime = ?, status = ? WHERE id = ?");
			preparedStatement.setInt(1, key.getUserID());
			preparedStatement.setString(2, key.getPublicKey());
			preparedStatement.setTimestamp(3, new Timestamp(key.getStartTime().getTime()));
			preparedStatement.setTimestamp(4, new Timestamp(key.getEndTime().getTime()));
			preparedStatement.setBoolean(5, key.isStatus());
			preparedStatement.setInt(6, key.getId());

			// Thực thi câu lệnh và kiểm tra số lượng bản ghi được cập nhật
			int rowsAffected = preparedStatement.executeUpdate();
			if (rowsAffected > 0) {
				isUpdated = true; // Đánh dấu là đã cập nhật thành công
			}
		} catch (Exception e) {
			e.printStackTrace();
			isUpdated = false;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}

		return isUpdated;
	}

	public List<Key> findKeysByAccountID(int userID) {
		List<Key> keys = new ArrayList<>();
		try {
			// Chuẩn bị câu lệnh SQL để lấy danh sách các key dựa trên userID
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("SELECT * FROM `key` WHERE userID = ?");
			preparedStatement.setInt(1, userID);
			ResultSet resultSet = preparedStatement.executeQuery();

			// Duyệt qua kết quả và thêm vào danh sách
			while (resultSet.next()) {
				Key key = new Key();
				key.setId(resultSet.getInt("id"));
				key.setUserID(resultSet.getInt("userID"));
				key.setPublicKey(resultSet.getString("publicKey"));
				key.setStartTime(resultSet.getDate("startTime"));
				key.setEndTime(resultSet.getDate("endTime"));
				key.setStatus(resultSet.getBoolean("status"));
				keys.add(key);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}

		return keys;
	}

	public static void main(String[] args) {
		System.out.println(new KeyModel().findByAccountID(1));
	}
}
