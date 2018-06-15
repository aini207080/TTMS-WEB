package com.xiyou.ttms.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.xiyou.ttms.bean.Data_dictExample;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Play;
import com.xiyou.ttms.bean.Schedule;
import com.xiyou.ttms.bean.ScheduleExample;
import com.xiyou.ttms.bean.ScheduleExample.Criteria;
import com.xiyou.ttms.bean.Seat;
import com.xiyou.ttms.bean.SeatExample;
import com.xiyou.ttms.bean.Studio;
import com.xiyou.ttms.bean.Ticket;
import com.xiyou.ttms.bean.TicketExample;
import com.xiyou.ttms.dao.Data_dictMapper;
import com.xiyou.ttms.dao.PlayMapper;
import com.xiyou.ttms.dao.ScheduleMapper;
import com.xiyou.ttms.dao.SeatMapper;
import com.xiyou.ttms.dao.StudioMapper;
import com.xiyou.ttms.dao.TicketMapper;

@Service
public class ScheduleService {

	@Autowired
	ScheduleMapper scheduleMapper;
	
	@Autowired
	TicketMapper ticketMapper;
	
	@Autowired
	StudioMapper studioMapper;
	
	@Autowired
	SeatMapper seatMapper;
	
	@Autowired
	PlayMapper playMapper;
	
	@Autowired
	Data_dictMapper data_dictMapper;
	
	public String save(Schedule schedule) {
		int TOF;
		if (schedule.getSchedId() != null) {
			TOF =   scheduleMapper.updateByPrimaryKeySelective(schedule);
		} else {
			TOF = scheduleMapper.insertSelective(schedule);
			List<Schedule> sche = scheduleMapper.selectByExample(null);
			int schedule_id=0;
			for(Schedule s:sche) {
				 if(s.getSchedId()>schedule_id)
					 schedule_id  = s.getSchedId();
			 }
			SeatExample example = new SeatExample();
			com.xiyou.ttms.bean.SeatExample.Criteria criteria = example.createCriteria();
			criteria.andStudioIdEqualTo(schedule.getStudioId());
			criteria.andSeatStatusEqualTo((short) 1);
			List<Seat> seat = seatMapper.selectByExample(example);
			for(Seat s:seat) {
				Ticket ticket=  new Ticket();
				ticket.setSchedId(schedule_id);
				ticket.setSeatId(s.getSeatId());
				ticket.setTicketPrice(schedule.getSchedTicketPrice());
				ticket.setTicketStatus((short) 0);
				ticketMapper.insertSelective(ticket);
			}		
		}
		JsonObject jsobjcet = new JsonObject();
		if (TOF != 0) {
			jsobjcet.addProperty("success", "true");
		} else {
			jsobjcet.addProperty("success", "true");
			jsobjcet.addProperty("errorMsg", "更新失败");
		}
		return jsobjcet.toString();
	}
	
	public String delete(String delIds) {
		String json = "";
		String str[] = delIds.split(",");
		
		for (int i = 0; i < str.length; i++) {
			Schedule f = scheduleMapper.selectByPrimaryKey(Integer.parseInt(str[i]));
			if (f == null) {
				json = "{\"errorIndex\":" + i + ",\"errorMsg\":" + "未找到符合该id的员工" + "}";
				return json;
			}
		}
		List<Integer> listIds = Arrays.asList(delIds.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
		ScheduleExample example2 = new ScheduleExample();
		example2.createCriteria().andSchedIdIn(listIds);
		int delNums = scheduleMapper.deleteByExample(example2);
		if (delNums > 0) {
			json = "{\"success\":" + "true" + ",\"delNums\":" + delNums + "}";
		} else {
			json = "{\"errorMsg\":" + "删除失败" + "}";
		}
		return json;
	}
	
	public String scheduleadd() {
		String s1 = "";
		String s2 = "";
		String p1 = "";
		String p2 = "";
		List<Studio> studios = studioMapper.selectByExample(null);
		List<Play> plays = playMapper.selectByExample(null);
		for (Studio s : studios) {
			s1 += s.getStudioId() + ",";
			s2 += s.getStudioName() + ",";
		}
		for(Play p : plays) {
			p1 += p.getPlayId() + ",";
			p2 += p.getPlayName() + ",";
		}
		return s1+"|"+s2+"|"+p1+"|"+p2;
	}
	
	public String searchByPage(PageBean pageBean, Schedule schedule){
		ScheduleExample example = new ScheduleExample();
		if (schedule.getPlayId()!=null) {
			example.createCriteria().andPlayIdEqualTo(schedule.getPlayId());
		}
		long total = scheduleMapper.countByExample(example);

		ScheduleExample example2 = new ScheduleExample();
		if (schedule.getPlayId()!=null) {
			example2.createCriteria().andPlayIdEqualTo(schedule.getPlayId());
		}
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		List<String> dates = new ArrayList<>();
		List<String> StudioNames = new ArrayList<>();
		List<String> PlayNames = new ArrayList<>();
		List<Schedule> schedules = scheduleMapper.selectByExample(example2);
		for(Schedule s:schedules) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(s.getSchedTime());
			dates.add(dateString);
			StudioNames.add(studioMapper.selectByPrimaryKey(s.getStudioId()).getStudioName());
			PlayNames.add(playMapper.selectByPrimaryKey(s.getPlayId()).getPlayName());
		}
		int i=0;
		String jsonArray = new Gson().toJson(scheduleMapper.selectByExample(example2));
		List<Map<String,String>> value = new Gson().fromJson(jsonArray,  new TypeToken<List<Map<String,String>>>(){}.getType());
		for(Map<String,String> v:value) {
			v.put("schedTime", dates.get(i));
			v.put("studioName", StudioNames.get(i));
			v.put("playName", PlayNames.get(i));
			i++;
		}
		String mapArray =  new Gson().toJson(value);
		String json = "{\"total\":" + total + ",\"rows\":" + mapArray + "}";
		return json;
	}

	public String saleschedule(PageBean pageBean, Schedule schedule) {

		ScheduleExample example = new ScheduleExample();
		Criteria criteria = example.createCriteria();
		if (schedule.getPlayId()!=null) {
			criteria.andPlayIdEqualTo(schedule.getPlayId());
		}
		long total = scheduleMapper.countByExample(example);

		ScheduleExample example2 = new ScheduleExample();
		Criteria criteria2 = example2.createCriteria();
		if (schedule.getPlayId()!=null) {
			criteria2.andPlayIdEqualTo(schedule.getPlayId());
		}
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		List<String> dates = new ArrayList<>();
		List<String> schedTicketPrice = new ArrayList<>();
		List<String> StudioNames = new ArrayList<>();
		List<String> PlayNames = new ArrayList<>();
		List<String> spareTicket = new ArrayList<>();
		List<String> studioRowCount = new ArrayList<>();
		List<String> studioColCount = new ArrayList<>();		
		List<Schedule> schedules = scheduleMapper.selectByExample(example2);
		for(Schedule s:schedules) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = formatter.format(s.getSchedTime());
			dates.add(dateString);
			schedTicketPrice.add(s.getSchedTicketPrice().toString());
			
			TicketExample example4 = new TicketExample();
			com.xiyou.ttms.bean.TicketExample.Criteria criteria4 = example4.createCriteria();
			criteria4.andSchedIdEqualTo(s.getSchedId());
			criteria4.andTicketStatusEqualTo((short) 1);
			List<Ticket> tickets = ticketMapper.selectByExample(example4);
			Data_dictExample example5 = new Data_dictExample();
			example5.createCriteria().andDictNameEqualTo("锁失效时间");
			String LockTime = data_dictMapper.selectByExample(example5).get(0).getDictValue();
			for(Ticket t:tickets) {
				Date now = new Date();
				long minutes = (now.getTime()-tickets.get(0).getTicketLockedTime().getTime())/(1000*60);
				if(LockTime!=null) {
					if(minutes>=Long.parseLong(LockTime)) {
					    t.setTicketStatus((short) 0);
					    t.setTicketLockedTime(null);
					    ticketMapper.updateByPrimaryKey(t);
					}
				}
			}
			
			
			TicketExample example3 = new TicketExample();
			com.xiyou.ttms.bean.TicketExample.Criteria criteria3 = example3.createCriteria();
			criteria3.andSchedIdEqualTo(s.getSchedId());
			criteria3.andTicketStatusEqualTo((short) 0);
			ticketMapper.countByExample(example3);
			

			
			spareTicket.add(ticketMapper.countByExample(example3)+"");
			StudioNames.add(studioMapper.selectByPrimaryKey(s.getStudioId()).getStudioName());
			PlayNames.add(playMapper.selectByPrimaryKey(s.getPlayId()).getPlayName());
			studioRowCount.add(studioMapper.selectByPrimaryKey(s.getStudioId()).getStudioRowCount()+"");
			studioColCount.add(studioMapper.selectByPrimaryKey(s.getStudioId()).getStudioColCount()+"");
		}
		int i=0;
		String jsonArray = new Gson().toJson(scheduleMapper.selectByExample(example2));
		List<Map<String,String>> value = new Gson().fromJson(jsonArray,  new TypeToken<List<Map<String,String>>>(){}.getType());
		for(Map<String,String> v:value) {
			v.put("schedTime", dates.get(i));
			v.put("schedTicketPrice", schedTicketPrice.get(i));
			v.put("spareTicket", spareTicket.get(i));
			v.put("studioName", StudioNames.get(i));
			v.put("playName", PlayNames.get(i));
			v.put("studioRowCount", studioRowCount.get(i));
			v.put("studioColCount", studioColCount.get(i));
			i++;
		}
		String mapArray =  new Gson().toJson(value);
		String json = "{\"total\":" + total + ",\"rows\":" + mapArray + "}";
		return json;
	}
}
