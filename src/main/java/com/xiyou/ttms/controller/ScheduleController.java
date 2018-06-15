package com.xiyou.ttms.controller;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Schedule;
import com.xiyou.ttms.service.ScheduleService;

@Controller
@RequestMapping("schedule")
public class ScheduleController {

	@Autowired
	ScheduleService scheduleService;
	
	@ResponseBody
	@RequestMapping("save")
	public String save(Schedule schedule,HttpServletRequest request) throws IOException, ParseException {
		return scheduleService.save(schedule);
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String delIds) throws IOException {
		return scheduleService.delete(delIds);
	}
	
	@ResponseBody
	@RequestMapping("scheduleadd")
	public String scheduleadd(HttpServletRequest request) throws IOException {
		return scheduleService.scheduleadd();
	}
	
	@ResponseBody
	@RequestMapping("saleschedule")
	public String saleschedule(HttpServletRequest request) throws IOException {
		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String playId = request.getParameter("playId");
		Schedule schedule = new Schedule();
		if (playId == null || playId == "") {
			schedule.setPlayId(null);
		}else {
			schedule.setPlayId(Integer.parseInt(playId));
		}
		PageBean pageBean = new PageBean(page, rows);
		return scheduleService.saleschedule(pageBean, schedule);
	}
	
	@ResponseBody
	@RequestMapping("searchByPage")
	public String searchByPage(HttpServletRequest request) throws Exception {

		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String playId = request.getParameter("playId");
		Schedule schedule = new Schedule();
		if (playId == null || playId == "") {
			schedule.setPlayId(null);
		}else {
			schedule.setPlayId(Integer.parseInt(playId));
		}
		PageBean pageBean = new PageBean(page, rows);
		return scheduleService.searchByPage(pageBean, schedule);
	}
}
