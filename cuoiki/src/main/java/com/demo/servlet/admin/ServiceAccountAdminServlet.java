package com.demo.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.entities.Account;
import com.demo.entities.AccountService;
import com.demo.entities.Duration;
import com.demo.entities.Log;
import com.demo.entities.Sale;
import com.demo.entities.Service;
import com.demo.ex.ConfigLog;
import com.demo.models.AccountDetailsModel;
import com.demo.models.AccountModel;
import com.demo.models.AccountPartialModel;
import com.demo.models.AccountServiceModel;
import com.demo.models.DurationModel;
import com.demo.models.FeedbackModel;
import com.demo.models.LogModel;
import com.demo.models.SaleModel;
import com.demo.models.ServiceModel;
import com.demo.models.SystemApartmentModel;
import com.google.gson.Gson;
@WebServlet({"/admin/serviceAccount"})
/**
 * Servlet implementation class AccountAdminServlet
 */
public class ServiceAccountAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServiceAccountAdminServlet() {
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
		} else if(action.equals("listAccount")){ 
			doGet_ListAccount(request, response);
		} else if(action.equals("cancelService")){ 
			doGet_CancelService(request, response);
		} else if(action.equals("newService")){ 
			doGet_NewService(request, response);
		} 
	}
	protected void doGet_Index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("admin", "../admin/serviceAccount.jsp");
		request.setAttribute("activeServiceAccount", "active");
		ServiceModel serviceModel = new ServiceModel();
		request.setAttribute("service", serviceModel.findAll());
		

		request.getRequestDispatcher("/WEB-INF/views/layout/admin.jsp").forward(request, response);
	}
	protected void doGet_ListAccount(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("admin", "../admin/listAccountService.jsp");
		request.setAttribute("activeServiceAccount", "active");
		String serviceID = request.getParameter("serviceID");
		
		request.getRequestDispatcher("/WEB-INF/views/layout/admin.jsp").forward(request, response);
	}
	protected void doGet_NewService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("admin", "../admin/newService.jsp");
		
		request.getRequestDispatcher("/WEB-INF/views/layout/admin.jsp").forward(request, response);
		
	}
	protected void doGet_CancelService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setAttribute("admin", "../admin/listAccountService.jsp");
		request.setAttribute("activeServiceAccount", "active");
		String accountServiceID = request.getParameter("accountServiceID");
		System.out.println(accountServiceID);
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		
		AccountService accountService = accountServiceModel.findById(Integer.parseInt(accountServiceID));
		System.out.println(accountService);
		accountService.setStatus(false);
		if(accountServiceModel.update(accountService)) {
			response.sendRedirect(request.getContextPath() + "/admin/serviceAccount?action=listAccount&serviceID=" + accountService.getServiceID());
		} else {
			
		}
	
	}

	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("newService")) {
			doPost_NewService(request, response);
		}
	}
	
	protected void doPost_NewService(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int accountID = Integer.parseInt(request.getParameter("accountID"));
		int serviceID = Integer.parseInt(request.getParameter("serviceID"));
		int durationID = Integer.parseInt(request.getParameter("durationID"));
		String saleID = request.getParameter("saleID");
		
		AccountService accountService = new AccountService();
		Calendar calendar = Calendar.getInstance();
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		AccountDetailsModel accountDetailsModel = new AccountDetailsModel();
		ServiceModel serviceModel = new ServiceModel();
		DurationModel durationModel = new DurationModel();
		
		// Xử lí end date
		int spaceIndex = durationModel.findById(durationID).getName().indexOf(' ');
	    String numberString = durationModel.findById(durationID).getName().substring(0, spaceIndex);
		calendar.add(Calendar.MONTH, Integer.parseInt(numberString));
		Date endDate = calendar.getTime();
		
		int beforeAddAccountService = accountServiceModel.findAll().size();
		LogModel logModel = new LogModel();
		Account accountAdmin = (Account) request.getSession().getAttribute("accountAdmin");
		int afterAddAccountService = 0;
		
		accountService.setAccountID(accountID);
		accountService.setServiceID(serviceID);
		accountService.setDurationID(durationID);
		accountService.setDescription(
				"Đăng kí gói: " + serviceModel.findByID(serviceID).getName() + " / " + durationModel.findById(durationID).getName());
		accountService.setCreated(new Date());
		accountService.setEndService(endDate);
		accountService.setSaleID(saleID == null ? 0 : Integer.parseInt(saleID));
		accountService.setStatus(true);
		AccountService account = accountServiceModel.findAccountByAccountId(accountID);
		if(accountServiceModel.register(accountService)) {
			account.setStatus(false);
			afterAddAccountService = durationModel.findAll().size();
			logModel.create(new Log(ConfigLog.clientPublicIP, "alert","AdminId: " + accountAdmin.getId() + " đã thêm người đăng kí gói dịch vụ " + accountService.getDescription() + " vào hệ thống",new ConfigLog().ipconfig(request).getCountryLong(), new java.util.Date(), "Số lượng người đăng kí dịch vụ gói " + serviceModel.findByID(serviceID).getName() + " trước khi thêm: " + beforeAddAccountService, "Số lượng người đăng kí dịch vụ gói " + serviceModel.findByID(serviceID).getName() + " sau khi thêm: " + afterAddAccountService));
			accountServiceModel.update(account);
			request.getSession().setAttribute("msg", "Đăng kí gói dịch vụ thành công");
			response.sendRedirect(request.getContextPath() + "/admin/serviceAccount");
		} else {
			request.getSession().setAttribute("msg", "Đăng kí gói dịch vụ thất bại");
			response.sendRedirect(request.getContextPath() + "/admin/serviceAccount");
		}
	}

}
