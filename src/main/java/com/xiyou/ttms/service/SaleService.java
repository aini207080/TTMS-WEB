package com.xiyou.ttms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Sale;
import com.xiyou.ttms.bean.SaleExample;
import com.xiyou.ttms.bean.SaleExample.Criteria;
import com.xiyou.ttms.dao.SaleMapper;

@Service
public class SaleService {
	
	@Autowired
	SaleMapper saleMapper;

	public String searchByPage(PageBean pageBean, Sale sale){
		SaleExample example = new SaleExample();
		Criteria criteria = example.createCriteria();
		if (sale.getEmpId()!=null) {
			criteria.andEmpIdEqualTo(sale.getEmpId());
		}
		long total =  saleMapper.countByExample(example);

		SaleExample example2 = new SaleExample();
		Criteria criteria2 = example2.createCriteria();
		if (sale != null && sale.getEmpId()!=null) {
			criteria2.andEmpIdEqualTo(sale.getEmpId());
		}
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		List<String> dates = new ArrayList<>();
		List<Sale> sales = saleMapper.selectByExample(example2);
		for(Sale s:sales) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(s.getSaleTime());
			dates.add(dateString);
		}
		int i=0;
		String jsonArray = new Gson().toJson(saleMapper.selectByExample(example2));
		List<Map<String,String>> value = new Gson().fromJson(jsonArray,  new TypeToken<List<Map<String,String>>>(){}.getType());
		for(Map<String,String> v:value) {
			v.put("saleTime", dates.get(i));
			i++;
		}
		String mapArray =  new Gson().toJson(value);
		String json = "{\"total\":" + total + ",\"rows\":" + mapArray + "}";
		return json;
	}
}
