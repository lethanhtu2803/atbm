package com.demo.servlet.admin;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.demo.entities.Account;
import com.demo.entities.Log;
import com.demo.ex.ConfigLog;
import com.demo.models.FeedbackModel;
import com.demo.models.LogModel;
@WebFilter(urlPatterns = {"/admin/*"})
/**
 * Servlet Filter implementation class AdminFilter
 */
public class AdminFilter extends HttpFilter implements Filter {
       
    /**
     * @see HttpFilter#HttpFilter()
     */
    public AdminFilter() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		HttpSession session = httpRequest.getSession();
		Account account = (Account) session.getAttribute("accountAdmin");
		LogModel logModel = new LogModel();
		if(account != null && (account.getRole() == 0 ||account.getRole() == 2) ) {
			chain.doFilter(request, response);
		
		} else {
			logModel.create(new Log(ConfigLog.clientPublicIP, "danger","Đăng nhập admin khi chưa có quyền",new ConfigLog().ipconfig(httpRequest).getCountryLong(), new java.util.Date(), null, null));
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/login");
		}
		
		// pass the request along the filter chain
		
		
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
