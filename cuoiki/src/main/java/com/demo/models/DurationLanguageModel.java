package com.demo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.demo.entities.ConnectDB;
import com.demo.entities.DurationLanguage;
import com.demo.entities.Post;
import com.demo.entities.ServiceLanguage;

public class DurationLanguageModel {

	public DurationLanguage find(int durationID, int languageID) {
		DurationLanguage post = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("select * from duration_language where durationID = ? and languageID = ?");
			preparedStatement.setInt(1, durationID);
			preparedStatement.setInt(2, languageID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				post = new DurationLanguage();
				post.setId(resultSet.getInt("id"));
				post.setLanguageID(resultSet.getInt("languageID"));
				post.setDurationID(resultSet.getInt("durationID"));
				post.setName(resultSet.getString("name"));
	
			}
		} catch (Exception e) {
			e.printStackTrace();
			post = null;
			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}

		return post;
	}
	
	public static void main(String[] args) {
		System.out.println(new DurationLanguageModel().find(12, 1));
	}
	
	
}
