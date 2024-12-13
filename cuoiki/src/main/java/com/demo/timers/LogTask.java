package com.demo.timers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import com.demo.entities.Account;
import com.demo.entities.AccountService;
import com.demo.entities.ConnectDB;
import com.demo.entities.Log;
import com.demo.entities.Sale;
import com.demo.helpers.MailHelper;
import com.demo.models.AccountModel;
import com.demo.models.AccountServiceModel;
import com.demo.models.LogModel;

public class LogTask extends TimerTask {
	private int oldLogSize;
	
    public LogTask(int oldLogSize) {
		super();
		this.oldLogSize = oldLogSize;
	}

	@Override
    public void run() {
	

		try {
				
				List<Log> logs = new ArrayList<Log>();
				PreparedStatement preparedStatement = ConnectDB.connection().prepareStatement("select * from log");
				ResultSet resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					Log log = new Log();
					log.setId(resultSet.getInt("id"));
					log.setIp(resultSet.getString("ip"));
					log.setLevel(resultSet.getString("level"));
					log.setNational(resultSet.getString("national"));
					log.setDescription(resultSet.getString("description"));
					log.setTime(resultSet.getTimestamp("time"));
					log.setBeforeValue(resultSet.getString("beforeValue"));
					log.setAfterValue(resultSet.getString("afterValue"));
					logs.add(log);
				}
		
				if (oldLogSize < logs.size()) {
					System.out.println("Đã có sự thay đổi: " + logs.get(logs.size() - 1));

					String input = logs.get(logs.size() - 1).getDescription();
					int startIndex = input.indexOf("ID: ") + 4;  
			        int endIndex = input.indexOf(".", startIndex); 

			        int id = Integer.parseInt(input.substring(startIndex, endIndex));
			        AccountServiceModel accountServiceModel = new AccountServiceModel();
			        AccountService accountService = accountServiceModel.findById(id);
					AccountModel accountModel = new AccountModel();
					Account account = accountModel.findAccountByAccountID(accountService.getAccountID());
					System.out.println(account.getEmail());
					
					MailHelper mailHelper = new MailHelper();
					mailHelper.MailHelper(account.getEmail(), "Cảnh báo có sự thay đổi dữ liệu", "Đã có sự thay đổi về thông tin đơn hàng của bạn: " + logs.get(logs.size() - 1));
					oldLogSize = logs.size();
				}

			

		} catch (Exception e) {
			e.printStackTrace();

			// TODO: handle exception
		} finally {
			ConnectDB.disconnect();
		}
    	
    }
}
