package com.demo.helpers;

import java.util.Timer;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpServletRequest;

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

		PostingTimer postingTimer = new PostingTimer();
		timer.scheduleAtFixedRate(postingTimer, 0, 5000);
		MSGTimer msgTimer = new MSGTimer(sce.getServletContext());
		timer.scheduleAtFixedRate(msgTimer, 0, 1000*60*60*23);
	
	}

}
