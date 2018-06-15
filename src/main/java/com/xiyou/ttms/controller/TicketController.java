package com.xiyou.ttms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xiyou.ttms.service.TicketService;

@Controller
@RequestMapping("ticket")
public class TicketController {

	@Autowired
	TicketService ticketService;
	
}
