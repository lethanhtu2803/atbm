package com.demo.timers;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TimerTask;

import com.demo.entities.AccountService;
import com.demo.entities.Post;
import com.demo.models.AccountServiceModel;
import com.demo.models.PostModel;

public class PostingTimer extends TimerTask {

	@Override
	public void run() {

		PostModel postModel = new PostModel();
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		if (postModel.listAccountID().size() > 0) {
			for (int i : postModel.listAccountID()) {
				if (isExists(i)) {

					List<Post> posts = postModel.findPostByAccountID(i);
					for (Post post : posts) {
						post.setStatus(true);
						postModel.update(post);
					}
					if (checkExpiredService(new Timestamp(new Date().getTime()))) {
						for (Post post : posts) {
							post.setStatus(false);
							postModel.update(post);
						}
					}

				} else if (!isExists(i)) {

					List<Post> posts = postModel.findPostByAccountID(i);

					for (Post post : posts) {
						post.setStatus(false);
						postModel.update(post);

					}
				}

			}
		}
	}

	public static boolean isExists(int accountID) {
		PostModel postModel = new PostModel();
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		for (AccountService accountService : accountServiceModel.findAll()) {
			if (accountService.getAccountID() == accountID) {
				return accountService.isStatus();
			}
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

}
