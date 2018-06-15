package com.xiyou.ttms.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiyou.ttms.bean.Data_dictExample;
import com.xiyou.ttms.bean.Sale;
import com.xiyou.ttms.bean.Sale_item;
import com.xiyou.ttms.bean.Seat;
import com.xiyou.ttms.bean.SeatExample;
import com.xiyou.ttms.bean.Ticket;
import com.xiyou.ttms.bean.TicketExample;
import com.xiyou.ttms.bean.TicketExample.Criteria;
import com.xiyou.ttms.dao.Data_dictMapper;
import com.xiyou.ttms.dao.EmployeeMapper;
import com.xiyou.ttms.dao.SaleMapper;
import com.xiyou.ttms.dao.Sale_itemMapper;
import com.xiyou.ttms.dao.SeatMapper;
import com.xiyou.ttms.dao.TicketMapper;
import com.xiyou.ttms.utils.StringUtil;

@Service
public class SeatService {

	@Autowired
	SeatMapper seatMapper;
	
	@Autowired
	TicketMapper ticketMapper;
	
	@Autowired
	Data_dictMapper data_dictMapper;
	
	@Autowired
	SaleMapper saleMapper;
	
	@Autowired
	Sale_itemMapper sale_itemMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	public String getstatus(String studio_id){
    	String json ="";
    	if(StringUtil.isNotEmpty(studio_id))
    	{
    		SeatExample example = new SeatExample();
    		example.createCriteria().andStudioIdEqualTo(Integer.parseInt(studio_id));
    		List<Seat> ss=seatMapper.selectByExample(example);
    		for(Seat s:ss) {
    			json += s.getSeatStatus()+",";
        	}
    	}else {
    		json = "false"; 
    	}
    	return json;	
	}
	
	public String getticketstatus(String studio_id,String schedId){
    	String json ="";
    	if(StringUtil.isNotEmpty(studio_id))
    	{
    		SeatExample example = new SeatExample();
    		example.createCriteria().andStudioIdEqualTo(Integer.parseInt(studio_id));
    		List<Seat> ss=seatMapper.selectByExample(example);
    		for(Seat s:ss) {
    			if(s.getSeatStatus()==-1||s.getSeatStatus()==0)
    				json += "-1,";
    			else {
    				TicketExample example2 = new TicketExample();
    				Criteria criteria = example2.createCriteria();
    				criteria.andSeatIdEqualTo(s.getSeatId());
    				if(schedId!=null) {
    					criteria.andSchedIdEqualTo(Integer.valueOf(schedId));
    				}
    				List<Ticket> tickets = ticketMapper.selectByExample(example2);
    				if(tickets.get(0).getTicketStatus() == 0) {
    					json += "0,";
    				}else if(tickets.get(0).getTicketStatus() == 9 ||tickets.get(0).getTicketStatus() == 1){
    					json += "1,";
    				}else{
    					json="false";
    				}
				
    			}
        	}
    	}else {
    		json = "false"; 
    	}
    	return json;	
	}
	
	//管理员设置座位状态保存
	public String savestatus(String studio_id,String delIds){
		int i=0;
		String json="";
		String str[]=delIds.split(",");
		int TOF=0;
		if(StringUtil.isNotEmpty(studio_id))
    	{
			SeatExample example = new SeatExample();
    		example.createCriteria().andStudioIdEqualTo(Integer.parseInt(studio_id));
    		List<Seat> ss=seatMapper.selectByExample(example);
    		for(Seat s:ss) {
    			s.setSeatStatus((short) Integer.parseInt(str[i]));
    			i++;
    			TOF=seatMapper.updateByPrimaryKeySelective(s);
    		}
    	}
		if(TOF!=0) {
			json = "{\"success\":" + true + "}"; 
		}else {
			json = "{\"success\":" + false + ",\"errormessage\":" + "更新失败" + "}"; 
		}		
		return json;	
	}

	public String saveticketstatus(String studio_id, String schedId, String saveStatus,String pay,String ticketPrice,String change,String empNo) {

		String json="";
		Data_dictExample example5 = new Data_dictExample();
		example5.createCriteria().andDictNameEqualTo("锁失效时间");
		String LockTime = data_dictMapper.selectByExample(example5).get(0).getDictValue();
		List<Integer> integers = new ArrayList<>();
		String str[]=saveStatus.split(",");
		SeatExample example = new SeatExample();
		com.xiyou.ttms.bean.SeatExample.Criteria criteria = example.createCriteria();
		criteria.andStudioIdEqualTo(Integer.parseInt(studio_id));
		List<Seat> ss=seatMapper.selectByExample(example);
		for(int i=0;i<str.length;i++) {
			System.out.println(ss.get(Integer.parseInt(str[i])-1).getSeatId());
			integers.add(ss.get(Integer.parseInt(str[i])-1).getSeatId());
		}
		TicketExample example2 = new TicketExample();
		Criteria criteria2 = example2.createCriteria();
		criteria2.andSeatIdIn(integers);
		criteria2.andSchedIdEqualTo(Integer.parseInt(schedId));
		List<Ticket> tickets = ticketMapper.selectByExample(example2);
		for(Ticket t:tickets) {
			if(t.getTicketStatus()== 1) {
				Date now = new Date();
				long minutes = (now.getTime()-tickets.get(0).getTicketLockedTime().getTime())/(1000*60);
				if(LockTime!=null) {
					if(minutes>=Long.parseLong(LockTime)) {
					    t.setTicketStatus((short) 9);
					    t.setTicketLockedTime(null);
					    ticketMapper.updateByPrimaryKey(t);
					    
					    json = "{\"success\":" + true + "}"; 
					}else {
						return json="{\"success\":" + false + ",\"errormessage\":" + "该位置已被其他人选择" + "}"; 
					}
				}
			}else if(t.getTicketStatus()== 9) {
				return json="{\"success\":" + false + ",\"errormessage\":" + "该位置已被购买" + "}"; 
			}else {
				t.setTicketStatus((short) 9);
				ticketMapper.updateByPrimaryKeySelective(t);
				json = "{\"success\":" + true + "}"; 
			}
		}
		
		Sale sale = new Sale();
		sale.setEmpId(employeeMapper.selectByEmpNo(empNo).getEmpId());
		sale.setSaleTime(new Date());
		sale.setSalePayment(new BigDecimal(pay));
		sale.setSaleStatus((short) 1);
		sale.setSaleType((short) 1);
		sale.setSaleChange(new BigDecimal(change));
		saleMapper.insertSelective(sale);
		
		long saleId=0;
		List<Sale> sales = saleMapper.selectByExample(null);
		for(Sale s:sales) {
			if(s.getSaleId()>saleId)
				saleId=s.getSaleId();
		}
		
		for(Ticket t:tickets) {
		    Sale_item sale_item = new Sale_item();
			sale_item.setSaleId(saleId);
			sale_item.setSaleItemPrice(new BigDecimal(ticketPrice));
			sale_item.setTicketId(t.getTicketId());
			sale_itemMapper.insertSelective(sale_item);
		}
		
		return json;
	}
}
