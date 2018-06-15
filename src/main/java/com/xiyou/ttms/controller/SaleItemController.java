package com.xiyou.ttms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Sale_item;
import com.xiyou.ttms.service.SaleItemService;

@Controller
@RequestMapping("saleItem")
public class SaleItemController {

	@Autowired
	SaleItemService saleItemService;
	
	@ResponseBody
	@RequestMapping("searchByPage")
	public String searchByPage(HttpServletRequest request) throws Exception {

		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String saleItemId = request.getParameter("saleItemId");
		Sale_item sale_item = new Sale_item();
		if (saleItemId == null || saleItemId == "") {
			sale_item.setSaleItemId(null);
		}else {
			sale_item.setSaleItemId(Long.valueOf(saleItemId));
		}
		PageBean pageBean = new PageBean(page, rows);
		return saleItemService.searchByPage(pageBean, sale_item);
	}
}
