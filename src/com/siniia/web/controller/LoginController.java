package com.siniia.web.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.siniia.app.dao.OrderDAO;
import com.siniia.app.dao.ProductsDAO;
import com.siniia.app.dbo.LoginBean;
import com.siniia.app.dbo.OrderDetails;
import com.siniia.app.dbo.OrdersReportsData;
import com.siniia.app.dbo.ProductDetails;
import com.siniia.app.dbo.UserProfile;
import com.siniia.app.utils.ActionUtils;

@Controller
public class LoginController {

	private static final Logger logger = LogManager.getLogger(LoginController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String login(HttpServletRequest request, Model model) {
		try {
			model.addAttribute("loginForm", new UserProfile());
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "redirect:/login";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String adminlogin(HttpServletRequest request, Model model) {
		try {
			model.addAttribute("loginForm", new UserProfile());
			LoginBean loginBean = new LoginBean();
			model.addAttribute("userForm", loginBean);
		} catch (Exception e) {
			logger.error(e, e);
		}
		return "userlogin";
	}

}
