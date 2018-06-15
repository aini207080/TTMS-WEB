package com.xiyou.ttms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiyou.ttms.dao.TicketMapper;

@Service
public class TicketService {

	@Autowired
	TicketMapper ticketMapper;
	
	
}
