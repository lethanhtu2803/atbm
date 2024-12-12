package com.demo.timers;

import java.util.TimerTask;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.demo.entities.Account;

public class MyTask extends TimerTask{
	private ServletContext request;
	
	public MyTask(ServletContext request) {
		super();
		this.request = request;
	}

	public MyTask() {
		super();
	}

	@Override
	public void run() {
		if( request.getAttribute("account") != null) {
			Account account = (Account) request.getAttribute("account");
			
			
		}
		/*
		 * ServletContext context = sce.getServletContext(); MyTask myTask = new
		 * MyTask(context); timer.scheduleAtFixedRate(myTask, 0, 100000);
		 */	
		
	}

}
