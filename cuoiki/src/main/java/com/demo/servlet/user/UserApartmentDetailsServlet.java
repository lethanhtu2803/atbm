package com.demo.servlet.user;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.demo.entities.Account;
import com.demo.entities.Post;
import com.demo.models.AccountDetailsModel;
import com.demo.models.AccountModel;
import com.demo.models.PostImageModel;
import com.demo.models.PostModel;
@WebServlet("/userapartmentdetails")
/**
 * Servlet implementation class HomeServlet
 */
public class UserApartmentDetailsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserApartmentDetailsServlet() {
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
		int id = Integer.parseInt(request.getParameter("id"));
		Post post = postModel.findPostByID(id);
		AccountModel accountModel = new AccountModel();
		AccountDetailsModel accountDetailsModel = new AccountDetailsModel();
		PostImageModel postImageModel = new PostImageModel();
		
		request.setAttribute("post", post);
		request.setAttribute("images", postImageModel.findPostImageByPostID(post.getId()));
		request.setAttribute("account", accountDetailsModel.findAccountByAccountID(post.getAccountid()));
		request.setAttribute("account1", accountModel.findAccountByAccountID(post.getAccountid()));
		request.setAttribute("p", "../user/userapartmentdetails.jsp");
		request.getRequestDispatcher("/WEB-INF/views/layout/user.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
