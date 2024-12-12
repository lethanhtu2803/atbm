package com.demo.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.entities.Account;
import com.demo.entities.Log;
import com.demo.ex.ConfigLog;
import com.demo.models.AccountModel;
import com.demo.models.AccountPartialModel;
import com.demo.models.FeedbackModel;
import com.demo.models.LogModel;
import com.demo.models.SystemApartmentModel;
import com.google.gson.Gson;
@WebServlet({"/superadmin/account"})
/**
 * Servlet implementation class AccountAdminServlet
 */
public class AccountAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private String oldFeedback = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AccountAdminServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action == null) {
			doGet_Index(request, response);
		} else if(action.equalsIgnoreCase("searchByName")) {
			doGet_SearchByName(request, response);
		} else if(action.equalsIgnoreCase("searchByPhone")) {
			doGet_SearchByPhone(request, response);
		} else if(action.equalsIgnoreCase("searchByVerify")) {
			doGet_SearchByVerify(request, response);
		} else if(action.equalsIgnoreCase("blockUser")) {
			doGet_BlockUser(request, response);
		} else if(action.equalsIgnoreCase("unblockUser")) {
			doGet_UnblockUser(request, response);
		} else if(action.equalsIgnoreCase("setAdmin")) {
			doGet_SetAdmin(request, response);
		} else if(action.equalsIgnoreCase("unAdmin")) {
			doGet_UnAdmin(request, response);
		}
	}
	protected void doGet_Index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("admin", "../admin/accounts.jsp");
		request.setAttribute("activeAccount", "active");
		AccountPartialModel accountPartialModel = new AccountPartialModel();
		request.setAttribute("accounts", accountPartialModel.findAll());
		FeedbackModel feedbackModel = new FeedbackModel();
		

		request.getRequestDispatcher("/WEB-INF/views/layout/admin.jsp").forward(request, response);
	}
	protected void doGet_SetAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogModel logModel = new LogModel();
		AccountModel accountModel = new AccountModel();
		int id = Integer.parseInt(request.getParameter("id"));
		Account account = accountModel.findAccountByAccountID(id);
		Account accountAdmin = (Account) request.getSession().getAttribute("accountAdmin");
		account.setRole(0);
		if(accountModel.update(account)) {
			logModel.create(new Log(ConfigLog.clientPublicIP, "alert","AdminID: " + accountAdmin.getId() + " đã cấp user có ID là: " + account.getId() + " lên admin",new ConfigLog().ipconfig(request).getCountryLong(), new java.util.Date(), "Vai trò: user", "Vai trò: admin"));
			request.getSession().setAttribute("msg", "Đã cấp admin thành công");
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
			
		} else {
			System.out.println(1);
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
		}

		
	}
protected void doGet_UnAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		LogModel logModel = new LogModel();
		Account accountAdmin = (Account) request.getSession().getAttribute("accountAdmin");
		AccountModel accountModel = new AccountModel();
		int id = Integer.parseInt(request.getParameter("id"));
		Account account = accountModel.findAccountByAccountID(id);
		account.setRole(1);
		if(accountModel.update(account)) {
			logModel.create(new Log(ConfigLog.clientPublicIP, "alert","AdminID: " + accountAdmin.getId() + " đã gỡ quyền admin cho user có ID là: " + account.getId(),new ConfigLog().ipconfig(request).getCountryLong(), new java.util.Date(), "Vai trò: admin", "Vai trò: user"));
			request.getSession().setAttribute("msg", "Đã gỡ admin thành công");
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
			
		} else {
			System.out.println(1);
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
		}

		
	}
	
	protected void doGet_SearchByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String name = request.getParameter("name");
		AccountPartialModel accountPartialModel = new AccountPartialModel();
		Gson gson = new Gson();
		writer.print(gson.toJson(accountPartialModel.searchByName(name)));
	}
	
	protected void doGet_SearchByVerify(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		int status = Integer.parseInt(request.getParameter("value"));
		Gson gson = new Gson();
		AccountPartialModel accountPartialModel = new AccountPartialModel();
		if(status == 0) {
			writer.print(gson.toJson(accountPartialModel.findAll()));
		} else if(status == 1) {
			writer.print(gson.toJson(accountPartialModel.checkVerify(true)));
		} else if(status == 2) {
			writer.print(gson.toJson(accountPartialModel.checkVerify(false)));
		} else {
			writer.print(gson.toJson(accountPartialModel.checkStatus(false)));
		}
	}
	
	protected void doGet_SearchByPhone(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json; charset=utf-8");
		request.setCharacterEncoding("utf-8");
		PrintWriter writer = response.getWriter();
		String phone = request.getParameter("phone");
		AccountPartialModel accountPartialModel = new AccountPartialModel();
		Gson gson = new Gson();
		writer.print(gson.toJson(accountPartialModel.searchByPhone(phone)));
	}
	
	protected void doGet_BlockUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountModel accountModel = new AccountModel();
		LogModel logModel = new LogModel();
		int id = Integer.parseInt(request.getParameter("id"));
		Account accountAdmin = (Account) request.getSession().getAttribute("accountAdmin"); 
		Account account = accountModel.findAccountByAccountID(id);
		account.setStatus(false);
		if(accountModel.update(account)) {
			logModel.create(new Log(ConfigLog.clientPublicIP, "alert","AdminId: " + accountAdmin.getId() + " đã chặn user có ID là: " + account.getId(),new ConfigLog().ipconfig(request).getCountryLong(), new java.util.Date(), "Trạng thái tài khoản: không bị chặn", "Trạng thái tài khoản: đã bị chặn"));
			request.getSession().setAttribute("msg", "Đã chặn thành công");
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
			
		} else {
			System.out.println(1);
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
		}
	}
	
	protected void doGet_UnblockUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		AccountModel accountModel = new AccountModel();
		LogModel logModel = new LogModel();
		Account accountAdmin = (Account) request.getSession().getAttribute("accountAdmin");
		int id = Integer.parseInt(request.getParameter("id"));
		Account account = accountModel.findAccountByAccountID(id);
		account.setStatus(true);
		if(accountModel.update(account)) {
			logModel.create(new Log(ConfigLog.clientPublicIP, "alert","AdminId: " + accountAdmin.getId() + " đã chặn user có ID là: " + account.getId(),new ConfigLog().ipconfig(request).getCountryLong(), new java.util.Date(), "Trạng thái tài khoản: đã bị chặn", "Trạng thái tài khoản: không bị chặn"));
			request.getSession().setAttribute("msg2", "Đã gỡ chặn thành công");
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
			
		} else {
			System.out.println(1);
			response.sendRedirect(request.getContextPath() + "/superadmin/account");
		}
	}

	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
