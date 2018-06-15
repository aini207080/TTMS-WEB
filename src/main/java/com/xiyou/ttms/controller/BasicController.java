package com.xiyou.ttms.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.xiyou.ttms.bean.User;
import com.xiyou.ttms.service.BasicService;

/**
 * 处理用户注册、登录、退出请求
 */

@Controller
@RequestMapping("basic")
public class BasicController {

	@Autowired
	BasicService basicService;


	// 注册验证
	@ResponseBody
	@RequestMapping(value = "registSubmit", method = RequestMethod.GET)
	public String registSubmit1(HttpServletRequest request, HttpSession session) throws Exception {
		String email = request.getParameter("email");
        String userName = request.getParameter("username");
		String result = basicService.ajax(email,userName);
		return result;
	}

	// 注册提交
	@RequestMapping(value = "registSubmit", method = RequestMethod.POST)
	public String registSubmit2(HttpServletRequest request, HttpSession session, String username, String password) throws Exception {
		// 调用service层插入数据库
		User user = basicService.register(username, password);
		session.setAttribute("login", "ok");
        session.setAttribute("currentUserName", user.getEmpNo());
		return "OrdinaryUser/nav";
	}

	@RequestMapping(value = "loginSubmit", method = RequestMethod.POST)
	public ModelAndView LoginSubmit(HttpServletRequest request, HttpSession session, String username, String password) {
		String page = "index";
		User user = basicService.findUserByNameAndPsw(username, password);
		if (user == null) {
			request.setAttribute("desc", "用户名或密码错误！");
			page="forward:/index.jsp";
		} else {
            session.setAttribute("login", "ok");      
            if(user.getType() == 1)
                session.setAttribute("Administrators", "ok");       
			session.setAttribute("currentUserName", user.getEmpNo());
			page="OrdinaryUser/nav";
		}
		return new ModelAndView(page);
	}

	@RequestMapping("logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/index.jsp";
	}
	
	
	//页面跳转控制
	@RequestMapping("OrdinaryUser/nav")
	public String nav() {
		return "OrdinaryUser/nav";
	}
	
	@RequestMapping("OrdinaryUser/my-profile")
	public String myprofile(){
		return "OrdinaryUser/my-profile";
	}
	
	@RequestMapping("OrdinaryUser/saleticket")
	public String saleticket(){
		return "OrdinaryUser/saleticket";
	}
	
	@RequestMapping("OrdinaryUser/saleschedule")
	public String saleschedule(){
		return "OrdinaryUser/saleschedule";
	}
	
	@RequestMapping("OrdinaryUser/saleseat")
	public String saleseat(){
		return "OrdinaryUser/saleseat";
	}
	
	@RequestMapping("Administrators/employees")
	public String employees() {
		return "Administrators/employees";
	}
	
	@RequestMapping("Administrators/seat")
	public String seat() {
		return "Administrators/seat";
	}
	
	@RequestMapping("Administrators/studio")
	public String studio() {
		return "Administrators/studio";
	}
	
	@RequestMapping("Administrators/user")
	public String user() {
		return "Administrators/user";
	}
	
	@RequestMapping("Administrators/play")
	public String play() {
		return "Administrators/play";
	}
	
	@RequestMapping("Administrators/schedule")
	public String schedule() {
		return "Administrators/schedule";
	}
	
	@RequestMapping("Administrators/sale")
	public String sale() {
		return "Administrators/sale";
	}
	
	@RequestMapping("Administrators/saleItem")
	public String saleItem() {
		return "Administrators/sale_item";
	}
}
