package com.demo.servlet.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.demo.helpers.UploadFileHelper;
import com.demo.models.ChatModel;
import com.demo.models.PostModel;
import com.demo.models.SystemApartmentImageModel;
import com.demo.models.SystemApartmentModel;
import com.google.gson.Gson;
@WebServlet({"/admin/chatadmin"})
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10,
        fileSizeThreshold = 1024 * 1024 * 10
)
/**
 * Servlet implementation class AccountAdminServlet
 */
public class ChatAdminServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatAdminServlet() {
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
		} else if(action.equalsIgnoreCase("loadMsg")) {
			doGet_LoadMsg(request, response);
		} 
	}
	protected void doGet_Index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ChatModel chatModel = new ChatModel();
		request.setAttribute("admin", "../admin/chatadmin.jsp");
		request.setAttribute("activeAdminSystem", "active");
		String id = request.getParameter("id");
		String n = request.getParameter("n");
		if(id == null) {
			id  = String.valueOf(chatModel.listUser().get(0).getUserID());
		}
		if(n == null) {
			n = String.valueOf("0");
		}
		request.setAttribute("chats", chatModel.findChatByUserID(Integer.parseInt(id), Integer.parseInt(n)));
		request.setAttribute("userID", chatModel.findChatByUserID(Integer.parseInt(id), 0).get(0).getUserID());
		request.setAttribute("n", n);
		System.out.println(id);
		request.getRequestDispatcher("/WEB-INF/views/layout/admin.jsp").forward(request, response);
	}	
	protected void doGet_LoadMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userId = request.getParameter("userID");
		System.out.println(userId);
		ChatModel chatModel = new ChatModel();
		PrintWriter printWriter = response.getWriter();
		Gson gson = new Gson();
		printWriter.print(gson.toJson(chatModel.findChatByUserID(Integer.parseInt(userId), 0)));
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaa");
		Part file = request.getPart("file");
		System.out.println(file);
		String chatFileUpload = UploadFileHelper.uploadFile("assets/user/images",request,file);
		System.out.println(chatFileUpload);
		response.getWriter().write(chatFileUpload);
	}

}
