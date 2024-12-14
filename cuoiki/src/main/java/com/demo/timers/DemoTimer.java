package com.demo.timers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.demo.entities.ConnectDB;
import com.demo.entities.Sale;
import com.demo.models.LogModel;
import com.demo.models.SaleModel;

public class DemoTimer {
	public static void main(String[] args) {
		LogModel logModel = new LogModel();
		int oldLogSize = logModel.findAll().size();
		System.out.println("oldLogSize: " + oldLogSize);
		   Timer timer = new Timer();
	        LogTask task = new LogTask(oldLogSize);
	        timer.scheduleAtFixedRate(task, 0, 3000); // 5 giây
	}
	
	
	
}
