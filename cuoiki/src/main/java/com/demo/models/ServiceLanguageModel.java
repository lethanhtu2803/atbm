package com.demo.models;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.demo.entities.ConnectDB;
import com.demo.entities.Post;
import com.demo.entities.ServiceLanguage;

public class ServiceLanguageModel {

	public ServiceLanguage find(int serviceID, int languageID) {
		ServiceLanguage post = null;
		try {
			PreparedStatement preparedStatement = ConnectDB.connection()
					.prepareStatement("select * from service_language where serviceID = ? and languageID = ?");
			preparedStatement.setInt(1, serviceID);
			preparedStatement.setInt(2, languageID);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				post = new ServiceLanguage();
				post.setId(resultSet.getInt("id"));
				post.setLanguageID(resultSet.getInt("languageID"));
				post.setPostID(resultSet.getInt("serviceID"));
				post.setIntroduction(resultSet.getString("introduction"));
				post.setDescription(resultSet.getString("description"));
			
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
		System.out.println(new ServiceLanguageModel().find(1, 2));
	}
	
	
}
