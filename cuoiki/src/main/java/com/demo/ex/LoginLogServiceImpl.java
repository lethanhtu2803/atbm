package com.demo.ex;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.demo.entities.ConnectDB;
import com.demo.entities.Log;
import com.demo.models.LogModel;

public class LoginLogServiceImpl{
	public static boolean insert(Log log) {
		LogModel logModel = new LogModel();
		for (Log log1 : logModel.findAll()) {
			if(log1.getIp().equals(log.getIp()) && log1.getLevel().equals(log.getLevel())) {
				System.out.println("aaa");
				logModel.updateTime(log);
				return false;
			}
		}
		
		return logModel.create(log);
		
	}


	
}
