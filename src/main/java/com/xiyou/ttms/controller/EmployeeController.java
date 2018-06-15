package com.xiyou.ttms.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xiyou.ttms.bean.Employee;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.service.EmployeeService;
import com.xiyou.ttms.utils.StringUtil;

/**
 * 处理员工CRUD请求
 */
@Controller
@RequestMapping("employees")
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	@ResponseBody
	@RequestMapping("ajax")
	public String ajax(HttpServletRequest request) throws IOException {
		String emp_no = (String) request.getSession().getAttribute("currentUserName");
		return employeeService.ajax(emp_no);
	}

	@ResponseBody
	@RequestMapping("save")
	public String save(HttpServletRequest request) throws IOException {

		String emp_id = request.getParameter("id");
		String emp_no = request.getParameter("number");
		String emp_name = request.getParameter("name");
		String emp_tel_num = request.getParameter("phone");
		String emp_addr = request.getParameter("address");
		String emp_email = request.getParameter("email");
		Employee employee = new Employee();
		if (StringUtil.isNotEmpty(emp_id)) {
			employee.setEmpId(Integer.parseInt(emp_id));
		}
		employee.setEmpNo(emp_no);
		employee.setEmpName(emp_name);
		employee.setEmpTelNum(emp_tel_num);
		employee.setEmpAddr(emp_addr);
		employee.setEmpEmail(emp_email);
		return employeeService.save(employee);
	}

	@ResponseBody
	@RequestMapping("passchange")
	public String passchange(HttpServletRequest request) throws IOException {

		String emp_no = request.getParameter("id");
		String passwordold = request.getParameter("passwordold");
		String passwordnew = request.getParameter("passwordnew");
		return employeeService.passchange(emp_no, passwordold, passwordnew);
	}

	@ResponseBody
	@RequestMapping("delete")
	public String delete(HttpServletRequest request) throws IOException {

		String delIds = request.getParameter("delIds");
		return employeeService.delete(delIds);
	}

	@RequestMapping("updateMyprofile")
	public String updateMyprofile(MultipartFile file,HttpServletRequest request)throws IOException, ServletException {

		String path = request.getSession().getServletContext().getRealPath("/")+"static/UserImages";
		System.out.println(path);
		request.setCharacterEncoding("UTF-8");
		Employee employee1 = new Employee();
		// 接收文本
		employee1.setEmpId(Integer.parseInt(request.getParameter("emp_id")));
		employee1.setEmpNo(request.getParameter("emp_no"));
		employee1.setEmpAddr(request.getParameter("emp_addr"));
		employee1.setEmpEmail(request.getParameter("emp_email"));
		employee1.setEmpName(request.getParameter("emp_name"));
		employee1.setEmpTelNum(request.getParameter("emp_tel_num"));
		try {
			employeeService.updateMyprofile(file, employee1,path);
			return "redirect:/basic/OrdinaryUser/my-profile";
		} catch (Exception e) {
			request.setAttribute("desc", "上传文件过大(限制5M)，或存在异常!");
			return "forward:/basic/OrdinaryUser/my-profile";
		}
	}

	@ResponseBody
	@RequestMapping("useradd")
	public String useradd(HttpServletRequest request) throws IOException {
		return employeeService.useradd();
	}

	@ResponseBody
	@RequestMapping("search")
	public String search(HttpServletRequest request) throws IOException {

		String emp_no = request.getParameter("fieldId");
		String emp_noValue = request.getParameter("fieldValue");
		return employeeService.search(emp_no, emp_noValue);
	}

	@ResponseBody
	@RequestMapping("searchByPage")
	public String searchByPage(HttpServletRequest request) throws Exception {

		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String emp_name = request.getParameter("empName");
		if (emp_name == null) {
			emp_name = "";
		}
		Employee employee = new Employee();
		employee.setEmpName(emp_name);
		PageBean pageBean = new PageBean(page, rows);
		return employeeService.searchByPage(pageBean, employee);
	}
}
