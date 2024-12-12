package com.demo.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.entities.Account;
import com.demo.models.ChatModel;
import com.demo.models.FeedbackModel;
import com.demo.models.SystemApartmentModel;
import com.google.gson.Gson;

/**
 * Servlet implementation class NotificationSerlet
 */
@WebServlet("/msgnotification")
public class MSGNotificationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MSGNotificationServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		Account account = (Account) request.getSession().getAttribute("account");
		PrintWriter writer = response.getWriter();
		String oldMSG = request.getParameter("oldMSG");
		int oldMSG1 = Integer.parseInt(oldMSG);
		ChatModel chatModel = new ChatModel();
		int newMSG = chatModel.findChat(account.getId(), 0).size();
		Gson gson = new Gson();
		if(request.getParameter("test") != null) {
			request.getSession().removeAttribute("msgNoti");
			request.getSession().setAttribute("msgNoti", chatModel.findChat(account.getId(), 0).size());
		}
		if(newMSG > oldMSG1) {
			writer.print(gson.toJson(newMSG > oldMSG1));
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
