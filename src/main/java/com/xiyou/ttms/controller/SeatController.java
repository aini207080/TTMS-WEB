package com.xiyou.ttms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyou.ttms.service.SeatService;

@Controller
@RequestMapping("seat")
public class SeatController {

	@Autowired
	SeatService seatService;
	
	@ResponseBody
	@RequestMapping("getstatus")
	public String getstatus(String studio_id){
    	return seatService.getstatus(studio_id);	
	}
	
	@ResponseBody
	@RequestMapping("getticketstatus")
	public String getticketstatus(String studio_id,String schedId){
    	return seatService.getticketstatus(studio_id,schedId);	
	}
	
	@ResponseBody
	@RequestMapping("savestatus")
	public String savestatus(String studio_id,String saveStatus){
		return seatService.savestatus(studio_id,saveStatus);	
	}
	
	@ResponseBody
	@RequestMapping("saveticketstatus")
	public String saveticketstatus(HttpServletRequest request,String studio_id,String schedId,String saveStatus,String pay,String ticketPrice,String change){
		String empNo = (String) request.getSession().getAttribute("currentUserName");
    	return seatService.saveticketstatus(studio_id,schedId,saveStatus,pay,ticketPrice,change,empNo);	
	}
}
