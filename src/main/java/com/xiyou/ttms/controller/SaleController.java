package com.xiyou.ttms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Sale;
import com.xiyou.ttms.service.SaleService;

@Controller
@RequestMapping("sale")
public class SaleController {

	@Autowired
	SaleService saleService;

	@ResponseBody
	@RequestMapping("searchByPage")
	public String searchByPage(HttpServletRequest request) throws Exception {

		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String empId = request.getParameter("empId");
		Sale sale = new Sale();
		if (empId == null||empId =="") {
			sale.setEmpId(null);
		}else {
			sale.setEmpId(Integer.parseInt(empId));
		}
		PageBean pageBean = new PageBean(page, rows);
		return saleService.searchByPage(pageBean, sale);
	}
}
