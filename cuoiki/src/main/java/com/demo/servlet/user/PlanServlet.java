package com.demo.servlet.user;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.demo.entities.Account;
import com.demo.entities.AccountService;
import com.demo.entities.Accountdetails;
import com.demo.entities.Duration;
import com.demo.entities.Invoice;
import com.demo.entities.Key;
import com.demo.entities.Log;
import com.demo.entities.Service;
import com.demo.ex.ConfigLog;
import com.demo.helpers.MD5;
import com.demo.helpers.PostHelper;
import com.demo.helpers.RSA;
import com.demo.models.AccountDetailsModel;
import com.demo.models.AccountServiceModel;
import com.demo.models.DurationModel;
import com.demo.models.InvoiceModel;
import com.demo.models.KeyModel;
import com.demo.models.LogModel;
import com.demo.models.ServiceModel;
import com.google.gson.Gson;

/**
 * Servlet implementation class ServiceServlet
 */
@WebServlet("/plan")
public class PlanServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PlanServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String action = request.getParameter("action");
		if (action == null) {
			doGet_Index(request, response);
		} else if (action.equalsIgnoreCase("buy")) {
			doGet_Buy(request, response);
		}
	}

	protected void doGet_Index(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		  String urlString = "https://portal.vietcombank.com.vn/Usercontrols/TVPortal.TyGia/pXML.aspx?b=10"; 

	        HttpURLConnection connection = null;
	        InputStream inputStream = null;
	        try {
	        
	            URL url = new URL(urlString);
	            connection = (HttpURLConnection) url.openConnection();
	            connection.setRequestMethod("GET");

	          
	            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
	          
	                inputStream = connection.getInputStream();

	              
	                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	                DocumentBuilder builder = factory.newDocumentBuilder();

	              
	                Document document = builder.parse(inputStream);

	           
	                NodeList nodeList = document.getElementsByTagName("Exrate");

	                String usdSellRate = null;

	          
	                for (int i = 0; i < nodeList.getLength(); i++) {
	                    Element element = (Element) nodeList.item(i);
	                    if ("USD".equals(element.getAttribute("CurrencyCode"))) {
	                        usdSellRate = element.getAttribute("Sell");
	                        break;
	                    }
	                }

	          
	                request.setAttribute("usdSellRate", usdSellRate);


	            } else {
	                throw new ServletException("Failed to fetch XML file: HTTP code " + connection.getResponseCode());
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new ServletException("Error processing XML file: " + e.getMessage(), e);
	        } finally {
	         
	            if (inputStream != null) {
	                try {
	                    inputStream.close();
	                } catch (IOException e) {
	                    e.printStackTrace();
	                }
	            }
	            if (connection != null) {
	                connection.disconnect();
	            }
	        }
		// TODO Auto-generated method stub
		request.setAttribute("activePlan", "active");
		request.setAttribute("p", "../user/plan.jsp");

		request.getRequestDispatcher("/WEB-INF/views/layout/user.jsp").forward(request, response);
	}

	protected void doGet_Buy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chuky = request.getParameter("chuky");
		System.out.println(chuky);
		
		
		
//		String serviceId = request.getParameter("id");
//		int durationId = Integer.parseInt(request.getParameter("duration"));
//		Calendar calendar = Calendar.getInstance();
//		AccountServiceModel accountServiceModel = new AccountServiceModel();
//		AccountDetailsModel accountDetailsModel = new AccountDetailsModel();
//		ServiceModel serviceModel = new ServiceModel();
//		DurationModel durationModel = new DurationModel();
//		Duration duration = new Duration();
//		int spaceIndex = durationModel.findById(durationId).getName().indexOf(' ');
//	    String numberString = durationModel.findById(durationId).getName().substring(0, spaceIndex);
//		calendar.add(Calendar.MONTH, Integer.parseInt(numberString));
//		Date endDate = calendar.getTime();
//		duration.setStatus(true);
//		LogModel logModel = new LogModel();
//		Account account = (Account) request.getSession().getAttribute("account");
//		if (account == null) {
//			request.getSession().setAttribute("msg", "Bạn cần đăng nhập để mua gói dịch vụ");
//			response.sendRedirect("plan");
//		} else {
//			Accountdetails accountdetails = accountDetailsModel.findAccountByAccountID(account.getId());
//			accountdetails = accountDetailsModel.findAccountByAccountID(account.getId());
//			if (accountdetails == null) {
//				request.getSession().setAttribute("msg", "Bạn cần phải cập nhật thông tin tài khoản để mua dịch vụ");
//				response.sendRedirect("account");
//			} else {
//				if (accountdetails.getBalance() > serviceModel.findByID(Integer.parseInt(serviceId)).getPrice()) {
//
//					AccountService accountService = new AccountService();
//					accountService.setAccountID(account.getId());
//					accountService.setServiceID(Integer.parseInt(serviceId));
//					accountService.setDurationID(durationId);
//					accountService.setDescription(
//							"Đăng kí gói: " + serviceModel.findByID(Integer.parseInt(serviceId)).getName() + " / " + durationModel.findById(durationId).getName());
//					accountService.setCreated(new Date());
//					accountService.setEndService(endDate);
//					accountService.setStatus(true);
//					accountService.setSaleID(0);
//					if (accountServiceModel.register(accountService)) {
//						logModel.create(new Log(ConfigLog.clientPublicIP, "alert","AccountID: " + account.getId() + " - đã mua gói dịch vụ",new ConfigLog().ipconfig(request).getCountryLong(), new java.util.Date(), null, "Gói dịch vụ đã mua là: " + serviceModel.findByID(Integer.parseInt(serviceId)).getName() + " / " + durationModel.findById(durationId).getName()));
//						accountdetails.setBalance(accountdetails.getBalance()
//								- serviceModel.findByID(Integer.parseInt(serviceId)).getPrice());
//						accountDetailsModel.update(accountdetails);
//						request.getSession().removeAttribute("accountdetails");
//						request.getSession().setAttribute("accountdetails", accountdetails);
//						request.getSession().setAttribute("msg", "Mua thành công");
//						response.sendRedirect("plan");
//					} else {
//						request.getSession().setAttribute("msg", "Mua thất bại");
//						response.sendRedirect("plan");
//					}
//
//				} else {
//					request.getSession().setAttribute("msg", "Bạn không đủ tiền để mua gói dịch vụ này");
//					response.sendRedirect("plan");
//				}
//			}
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equals("hash")) {
			doPost_Hash(request, response);
		} else if(action.equals("checkChuKy")){
			doPost_CheckChuKy(request, response);
			
		}
	}
	protected void doPost_Hash(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("hash");
		PrintWriter printWriter = response.getWriter();
		
		String serviceId = request.getParameter("id");
		int durationId = Integer.parseInt(request.getParameter("durationID"));
		Calendar calendar = Calendar.getInstance();
		AccountServiceModel accountServiceModel = new AccountServiceModel();
		AccountDetailsModel accountDetailsModel = new AccountDetailsModel();
		ServiceModel serviceModel = new ServiceModel();
		DurationModel durationModel = new DurationModel();
		Duration duration = new Duration();
		int spaceIndex = durationModel.findById(durationId).getName().indexOf(' ');
	    String numberString = durationModel.findById(durationId).getName().substring(0, spaceIndex);
		calendar.add(Calendar.MONTH, Integer.parseInt(numberString));
		Date endDate = calendar.getTime();
		duration.setStatus(true);
		LogModel logModel = new LogModel();
		Account account = (Account) request.getSession().getAttribute("account");
		if (account == null) {
			request.getSession().setAttribute("msg", "Bạn cần đăng nhập để mua gói dịch vụ");
			response.sendRedirect("plan");
		} else {
			Accountdetails accountdetails = accountDetailsModel.findAccountByAccountID(account.getId());
			accountdetails = accountDetailsModel.findAccountByAccountID(account.getId());
			if (accountdetails == null) {
				request.getSession().setAttribute("msg", "Bạn cần phải cập nhật thông tin tài khoản để mua dịch vụ");
				response.sendRedirect("account");
			} else {
				if (true) {

					AccountService accountService = new AccountService();
					accountService.setAccountID(account.getId());
					accountService.setServiceID(Integer.parseInt(serviceId));
					accountService.setDurationID(durationId);
					accountService.setDescription(
							"Đăng kí gói: " + serviceModel.findByID(Integer.parseInt(serviceId)).getName() + " / " + durationModel.findById(durationId).getName());
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.0");
					accountService.setCreated(new Date());
					accountService.setEndService(endDate);
					accountService.setStatus(true);
					accountService.setSaleID(0);
					Gson gson = new Gson();
					MD5 md5 = new MD5();
					String hash = md5.hashMD5(gson.toJson(accountService));
					request.getSession().setAttribute("accountService", accountService);
					System.out.println(gson.toJson(accountService));
					System.out.println(accountService);
					System.out.println(hash);
					printWriter.write(hash);
//					if (accountServiceModel.register(accountService)) {
//						logModel.create(new Log(ConfigLog.clientPublicIP, "alert","AccountID: " + account.getId() + " - đã mua gói dịch vụ",new ConfigLog().ipconfig(request).getCountryLong(), new java.util.Date(), null, "Gói dịch vụ đã mua là: " + serviceModel.findByID(Integer.parseInt(serviceId)).getName() + " / " + durationModel.findById(durationId).getName()));
//						accountdetails.setBalance(accountdetails.getBalance()
//								- serviceModel.findByID(Integer.parseInt(serviceId)).getPrice());
//						accountDetailsModel.update(accountdetails);
//						request.getSession().removeAttribute("accountdetails");
//						request.getSession().setAttribute("accountdetails", accountdetails);
//						request.getSession().setAttribute("msg", "Mua thành công");
//						
//					} else {
//						request.getSession().setAttribute("msg", "Mua thất bại");
//						response.sendRedirect("plan");
//					}

				} else {
					request.getSession().setAttribute("msg", "Bạn không đủ tiền để mua gói dịch vụ này");
					response.sendRedirect("plan");
				}
			}
		}
	}
	protected void doPost_CheckChuKy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String chuky = request.getParameter("chuky");
		System.out.println("chuky: " + chuky);
		KeyModel keyModel = new KeyModel();
		Account account = (Account) request.getSession().getAttribute("account");
		System.out.println(account.getId());
		RSA rsa = new RSA();
		Key key = keyModel.findByAccountID(account.getId());
		if(key != null) {
			try {
				System.out.println("chu ky: " + chuky.trim());
				System.out.println("public key: " + key.getPublicKey().trim());
				System.out.println(rsa.decryptWithPublicKey(chuky.trim(), key.getPublicKey().trim()));
				if(request.getSession().getAttribute("accountService") != null) {
					System.out.println(request.getSession().getAttribute("accountService"));
					AccountService accountService = (AccountService) request.getSession().getAttribute("accountService");
					accountService.setKey(chuky.trim());
					System.out.println(accountService);
					AccountServiceModel accountServiceModel = new AccountServiceModel();
					AccountService existAccountService = accountServiceModel.findAccountByAccountId(account.getId());
					System.out.println("existAccountService: " + existAccountService);
					if(existAccountService != null) {
						System.out.println("xoa thanh cong");
						boolean status = accountServiceModel.deleteByServiceID(existAccountService.getId());
						System.out.println(status);
					} else {
						System.out.println("xoa that bai");
					}
					if(accountServiceModel.register(accountService)) {
						System.out.println("thanh cong");
						request.getSession().setAttribute("msg", "Đăng ký gói thành công");
						response.sendRedirect("plan");
					} else {
						System.out.println("k thanh cong");
					}
				}
				
				
			} catch (Exception e) {
				request.getSession().setAttribute("msg", "Thao tác xác nhận chữ ký không đúng, vui lòng đăng ký gói lại");
				response.sendRedirect("plan");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			request.getSession().setAttribute("msg", "Thao tác xác nhận chữ ký không đúng, vui lòng đăng ký gói lại");
			response.sendRedirect("plan");
		}
	
		
		
	}

}
