package com.demo.timers;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletContext;

import com.demo.entities.AccountService;
import com.demo.entities.Chat;
import com.demo.entities.Post;
import com.demo.models.AccountServiceModel;
import com.demo.models.ChatModel;
import com.demo.models.PostModel;

public class MSGTimer extends TimerTask {
	private ServletContext request;

	public MSGTimer(ServletContext request) {
		super();
		this.request = request;
	}

	@Override
	public void run() {
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		ChatModel chatModel = new ChatModel();
		LocalDate date2 = LocalDate.now(); // Ngày hiện tại
		List<AccountService> accountServices = new ArrayList<AccountService>();
		for (AccountService accountService : accountServiceModel.findAll()) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(accountService.getEndService());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			LocalDate date1 = LocalDate.of(year, month + 1, day);
			long daysBetween = ChronoUnit.DAYS.between(date2, date1);
			if (daysBetween == 5 || daysBetween == 1) {
				chatModel.newChat(new Chat(accountService.getAccountID(), 29,
						"Gói cước của bạn sắp hết hạn, nếu muốn gia hạn hãy reply!", 0, new Date()));
				accountServices.add(accountService);
				request.setAttribute("notiService", "Gói cước của bạn sắp hết hạn, hãy liên hệ với admin để trao đổi về dịch vụ mới!");
				request.setAttribute("services", accountServices);
			}
		}
	}
	public static void main(String[] args) {
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		ChatModel chatModel = new ChatModel();
		LocalDate date2 = LocalDate.now(); // Ngày hiện tại
		List<AccountService> accountServices = new ArrayList<AccountService>();
		for (AccountService accountService : accountServiceModel.findAll()) {

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(accountService.getEndService());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH);
			int day = calendar.get(Calendar.DAY_OF_MONTH);
			LocalDate date1 = LocalDate.of(year, month + 1, day);
			long daysBetween = ChronoUnit.DAYS.between(date2, date1);
			if (daysBetween == 5 || daysBetween == 1) {
				chatModel.newChat(new Chat(accountService.getAccountID(), 29,
						"Gói cước của bạn còn hạn 5 ngày, nếu muốn gia hạn hãy reply!", 0, new Date()));
				accountServices.add(accountService);
			}
		}
		System.out.println(accountServices);
	}
	
}
	