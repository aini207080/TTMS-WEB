package com.xiyou.ttms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Sale_item;
import com.xiyou.ttms.bean.Sale_itemExample;
import com.xiyou.ttms.bean.Sale_itemExample.Criteria;
import com.xiyou.ttms.dao.Sale_itemMapper;

@Service
public class SaleItemService {

	@Autowired
	Sale_itemMapper sale_itemMapper;
	
	public String searchByPage(PageBean pageBean, Sale_item sale_item){
		Sale_itemExample example = new Sale_itemExample(); 
		Criteria criteria = example.createCriteria();
		if (sale_item.getSaleItemId()!=null) {
			criteria.andSaleItemIdEqualTo(sale_item.getSaleItemId());
		}
		long total = sale_itemMapper.countByExample(example);

		Sale_itemExample example2 = new Sale_itemExample(); 
		Criteria criteria2 = example2.createCriteria();
		if (sale_item != null && sale_item.getSaleItemId()!=null) {
			criteria2.andSaleItemIdEqualTo(sale_item.getSaleItemId());
		}
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		String jsonArray = new Gson().toJson(sale_itemMapper.selectByExample(example2));
		String json = "{\"total\":" + total + ",\"rows\":" + jsonArray + "}";
		return json;
	}
}
