package com.demo.helpers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

import com.demo.entities.ConnectDB;
import com.demo.entities.Log;
import com.demo.entities.Sale;
import com.demo.models.LogModel;
import com.demo.models.SaleModel;
import com.demo.timers.LogTask;
import com.demo.timers.MSGTimer;
import com.demo.timers.MyTask;
import com.demo.timers.PostingTimer;

@WebListener
/**
 * Application Lifecycle Listener implementation class TimerHelper
 *
 */
public class TimerHelper implements ServletContextListener {
	private Timer timer;

	/**
	 * Default constructor.
	 */
	public TimerHelper() {
		timer = new Timer();
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {

		if (timer != null) {
			timer.cancel();
		}
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {

//		PostingTimer postingTimer = new PostingTimer();
//		timer.scheduleAtFixedRate(postingTimer, 0, 5000);
//		MSGTimer msgTimer = new MSGTimer(sce.getServletContext());
//		timer.scheduleAtFixedRate(msgTimer, 0, 1000 * 60 * 60 * 23);
		LogModel logModel = new LogModel();
		if(logModel.findAll() != null) {
			System.out.println("logmodel.findall");
			int oldLogSize = logModel.findAll().size();
	        LogTask task = new LogTask(oldLogSize);
	        timer.scheduleAtFixedRate(task, 0, 3000); 
		} else {
			System.out.println("logmodel.findall null");
		}
		
		


		

	}

}
