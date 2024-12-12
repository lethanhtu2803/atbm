package com.demo.servlet.user;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.entities.Account;
import com.demo.entities.Feedback;
import com.demo.entities.Log;
import com.demo.ex.ConfigLog;
import com.demo.ex.CreateLogService;
import com.demo.ex.LoginLogServiceImpl;
import com.demo.helpers.IPAddressUtil;
import com.demo.models.FeedbackModel;
import com.demo.models.LogModel;
import com.demo.models.PostModel;
import com.google.gson.Gson;

//import com.demo.models.PostModel;
@WebServlet("/home")
/**
 * Servlet implementation class HomeServlet
 */
public class HomeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
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
		}
	}
	protected void doGet_Index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PostModel postModel = new PostModel();
		request.setAttribute("posts", postModel.findTopSix());
		request.setAttribute("activeHome", "active");
		request.setAttribute("p", "../user/home.jsp");
		request.getRequestDispatcher("/WEB-INF/views/layout/user.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if(action.equalsIgnoreCase("submitFeedback")) {
			doPost_SubmitFeedback(request, response);
		}
	}
	protected void doPost_SubmitFeedback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		// TODO Auto-generated method stub
		LogModel logModel = new LogModel();
		Account account = (Account) request.getSession().getAttribute("account");
		if(account != null ) {
			
			FeedbackModel feedBackModel = new FeedbackModel();
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String subject = request.getParameter("subject");
			String message = request.getParameter("message");
			Feedback feedback = new Feedback();
			feedback.setAccountid(account.getId());
			feedback.setCreated(new Date());
			feedback.setDescription(new String(message.getBytes("ISO-8859-1"), "UTF-8"));
			feedback.setSubject(new String(subject.getBytes("ISO-8859-1"), "UTF-8"));
			if(feedBackModel.submitFeedback(feedback)) {
				logModel.create(new Log(IPAddressUtil.getPublicIPAddress(),"alert","AccountID: " + account.getId() + " - đã để lại 1 ý kiến", ConfigLog.ipconfig(request).getCountryLong(), new Timestamp(new Date().getTime()),null,null));
				request.getSession().setAttribute("msg","Cảm ơn đã đóng góp ý kiến cho hệ thống.Kính chúc quý khách một ngày tốt lành");
				response.sendRedirect("home#form-submit");
			} else {
				request.getSession().setAttribute("msg","Bình luận không thành công");
			}
		} else {
			request.getSession().setAttribute("msg","Bạn cần đăng nhập tài khoản để thực hiện chức năng này");
			response.sendRedirect("home#form-submit");
		}
	}

}
