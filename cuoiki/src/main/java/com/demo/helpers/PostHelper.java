package com.demo.helpers;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.demo.entities.AccountService;
import com.demo.entities.Post;
import com.demo.models.AccountServiceModel;
import com.demo.models.PostModel;
import com.demo.models.ServiceModel;

public class PostHelper {
	public static boolean isExixts(int accountid) {
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		for (AccountService account : accountServiceModel.findAll()) {
			if (account.getAccountID() == accountid) {
				return account.isStatus();
			}
		}
		return false;
	}

	public boolean isPostByPlan(int accountid, Timestamp current) {
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		ServiceModel serviceModel = new ServiceModel();
		for (AccountService accountService : accountServiceModel.findAll()) {
			int limitPost = serviceModel.findByID(accountService.getServiceID()).getPostNumber();
			if (isExixts(accountid)) {
//				if (serviceModel.findByID(accountService.getServiceID()).getPostNumber() >= postModel
//						.getNumberPostByAccount(accountid)
//						|| serviceModel.findByID(accountService.getServiceID()).getId() == 4) {
//					return true;
//				}
				if (limitPost != -1) {
					if ((isValidPost(accountid, current) && limitPost > countValidPost(accountid))
							|| serviceModel.findByID(accountService.getServiceID()).getId() == 4) {
						return true;
					}
				} else if (limitPost == -1) {
					if (isValidPost(accountid, current)) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// Hàm tính toán ngày kết thúc dựa vào các gói
	public static void endServiceCaculate(int id, int accountid) {
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		Calendar calendar = Calendar.getInstance();
		Date endDay = new Date();
		AccountService accountService = accountServiceModel.findAccountByAccountId(accountid);
		Timestamp createdTimestamp = new Timestamp(accountService.getCreated().getTime());
		calendar.setTime(createdTimestamp);
		int durationKey = accountServiceModel.findAccountById(accountService.getId(), accountService.getDurationID())
				.getDurationID();
		switch (durationKey) {
		case 12:
			calendar.add(Calendar.MONTH, 1);
			endDay = calendar.getTime();
			accountService.setEndService(endDay);
			accountServiceModel.update(accountService);
			break;
		case 13:
			calendar.add(Calendar.MONTH, 6);
			endDay = calendar.getTime();
			accountService.setEndService(endDay);
			accountServiceModel.update(accountService);
			break;
		case 14:
			calendar.add(Calendar.MONTH, 12);
			endDay = calendar.getTime();
			accountService.setEndService(endDay);
			accountServiceModel.update(accountService);
			break;
		default:
			break;
		}
	}

	// Kiểm tra bài post mới đăng có hợp lệ theo gói plan không
	public static boolean isValidPost(int accountid, Timestamp current) {
		PostModel postModel = new PostModel();
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		AccountService accountService = accountServiceModel.findAccountByAccountId(accountid);
//		Post post = postModel.findNewPostByID(accountid);
		Timestamp startTime = new Timestamp(accountService.getCreated().getTime());
		Timestamp endTime = new Timestamp(accountService.getEndService().getTime());
		if (isWithinRange(startTime, endTime, current)) {
			return true;
		}

		return false;
	}

	// Hàm đếm số bài viết đã đăng trong khoảng tg hợp lê
	public static int countValidPost(int accountid) {
		int result = 0;
		PostModel postModel = new PostModel();
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		AccountService accountService = accountServiceModel.findAccountByAccountId(accountid);
		Timestamp startTime = new Timestamp(accountService.getCreated().getTime());
		Timestamp endTime = new Timestamp(accountService.getEndService().getTime());
		for (Post post : postModel.findPostByAccountID(accountid)) {
			if (isWithinRange(startTime, endTime, new Timestamp(post.getPostdate().getTime()))) {
				result++;
			}
		}

		return result;
	}

	// Hàm kiểm tra có nằm trong mốc thời gian qui định không
	public static boolean isWithinRange(Timestamp start, Timestamp end, Timestamp current) {
		return !current.before(start) && !current.after(end);
	}

	// Hàm kiểm tra xem người dùng đã mua gói dịch vụ để đăng bài chưa
	public static boolean checkUserBuyService(int accountid) {
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		if (accountServiceModel.findAccountByAccountId(accountid) != null) {
			return true;
		}

		return false;
	}

	// Hàm check đã hết hạn gói dịch vụ chưa
	public static boolean checkExpiredService(Timestamp current) {
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		for (AccountService accountService : accountServiceModel.findAll()) {
			if (current.after(accountService.getEndService())) {
				accountService.setStatus(false);
				accountServiceModel.update(accountService);
				return true;
			}
		}
		return false;
	}

	public static void main(String[] args) {
//		PostHelper postHelper = new PostHelper();
//		System.out.println(postHelper.isPostByPlan(1, new Timestamp(new Date().getTime())));
//		endServiceCaculate(22, 1);
//		System.out.println(countValidPost(1));
//		System.out.println(checkUserBuyService(2));
		System.out.println(checkExpiredService(new Timestamp(new Date().getTime())));

	}
}
