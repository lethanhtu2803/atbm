package com.demo.servlet.user;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.demo.entities.Account;
import com.demo.entities.Log;
import com.demo.entities.Post;
import com.demo.entities.PostImage;
import com.demo.ex.ConfigLog;
import com.demo.helpers.IPAddressUtil;
import com.demo.helpers.UploadFileHelper;
import com.demo.models.LogModel;
import com.demo.models.PostImageModel;
import com.demo.models.PostModel;
import com.google.gson.Gson;
@WebServlet("/mypost")
/**
 * Servlet implementation class HomeServlet
 */
public class MyPostServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyPostServlet() {
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
		} else if(action.equalsIgnoreCase("deletePost")) {
			doGet_DeletePost(request, response);
		}
	}
	protected void doGet_Index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostModel postModel = new PostModel();
		Account account = (Account) request.getSession().getAttribute("account");
		if(account != null) {
			request.setAttribute("postaccount", postModel.findPostByAccountID(account.getId()));
		}
		request.setAttribute("p", "../user/mypost.jsp");
		request.getRequestDispatcher("/WEB-INF/views/layout/user.jsp").forward(request, response);
	}
	
	protected void doGet_DeletePost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PostModel postModel = new PostModel();
		Account account = (Account) request.getSession().getAttribute("account");
		List<Post> listPostBeforeDelete = postModel.findPostByAccountID(account.getId());
		Gson gson = new Gson();
		PostImageModel postImageModel = new PostImageModel();
		LogModel logModel = new LogModel();
		int id = Integer.parseInt(request.getParameter("id"));
		if(postImageModel.delete(id)) {
			if(postModel.delete(id)) {
				List<Post> listPostAfterDelete = postModel.findPostByAccountID(account.getId());
				logModel.create(new Log(IPAddressUtil.getPublicIPAddress(),"alert","AccountID: " + account.getId() + " - đã xóa bài đăng căn hộ có id là: " + id, ConfigLog.ipconfig(request).getCountryLong(), new Timestamp(new Date().getTime()),gson.toJson(listPostBeforeDelete),gson.toJson(listPostAfterDelete)));
				request.getSession().setAttribute("msg", "Đã xóa bài đăng thành công");
				response.sendRedirect(request.getContextPath() + "/admin/postapartment");
			} else {
				request.getSession().setAttribute("msg", "Xóa bài đăng không thành công");
				response.sendRedirect(request.getContextPath() + "/admin/postapartment");
			}
		} else {
			if(postModel.delete(id)) {
				List<Post> listPostAfterDelete = postModel.findPostByAccountID(account.getId());
				logModel.create(new Log(IPAddressUtil.getPublicIPAddress(),"alert","AccountID: " + account.getId() + " - đã xóa bài đăng căn hộ có id là: " + id, ConfigLog.ipconfig(request).getCountryLong(), new Timestamp(new Date().getTime()),gson.toJson(listPostBeforeDelete),gson.toJson(listPostAfterDelete)));
				request.getSession().setAttribute("msg", "Đã xóa bài đăng thành công");
				response.sendRedirect(request.getContextPath() + "/admin/postapartment");
			} else {
				request.getSession().setAttribute("msg", "Xóa bài đăng không thành công");
				response.sendRedirect(request.getContextPath() + "/admin/postapartment");
			}
		}
	}
	
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
	

}
