package com.xiyou.ttms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.User;
import com.xiyou.ttms.service.UserService;
import com.xiyou.ttms.utils.StringUtil;

@Controller
@RequestMapping("user")
public class UserController {

	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping("save")
	public String save(HttpServletRequest request) {
		String id = request.getParameter("id");
		String emp_no = request.getParameter("username");
		String emp_pass = request.getParameter("password");
		String type = request.getParameter("type");
		String head_path = request.getParameter("head_path");
		User user = new User();
		user.setEmpNo(emp_no);
		user.setEmpPass(emp_pass);
		if (StringUtil.isNotEmpty(type)) {
			user.setType((byte) Integer.parseInt(type));
		}
		user.setHeadPath(head_path);
		return userService.save(id, user);
	}

	// 将所有没头像的用户设置为初始头像
	@ResponseBody
	@RequestMapping("Head")
	public String Head(HttpServletRequest request) {
		return userService.Head();
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public String delete(HttpServletRequest request){
		String delIds = request.getParameter("delIds");
		return userService.delete(delIds);
	}
	
	@ResponseBody
	@RequestMapping("searchByPage")
	public String searchByPage(HttpServletRequest request) throws Exception {

		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String emp_name = request.getParameter("emp_no");
		if (emp_name == null) {
			emp_name = "";
		}
		User user = new User();
		user.setEmpNo(emp_name);
		PageBean pageBean = new PageBean(page, rows);
		return userService.searchByPage(pageBean, user);
	}
}
