package com.demo.ex;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.demo.helpers.IP2LocationService;
import com.demo.helpers.IPAddressUtil;
import com.ip2location.IPResult;

public class ConfigLog {
	public static String clientPublicIP = IPAddressUtil.getPublicIPAddress();
	public static IPResult ipconfig(HttpServletRequest request) {
		IP2LocationService ip2LocationService;
		String dbPath = request.getServletContext().getRealPath("/WEB-INF/IP2LOCATION-LITE-DB11.BIN");
        ip2LocationService = new IP2LocationService(dbPath);
		String clientPublicIP = IPAddressUtil.getPublicIPAddress();
		IPResult result = ip2LocationService.lookup(clientPublicIP);
		return result;
	}
}
