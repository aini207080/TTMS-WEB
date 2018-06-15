package com.xiyou.ttms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Play;
import com.xiyou.ttms.service.PlayService;

@Controller
@RequestMapping("play")
public class PlayController {

	@Autowired
	PlayService playService;
	
	@ResponseBody
	@RequestMapping("save")
	public String save(MultipartFile file,HttpServletRequest request,Play play) throws IOException {
		String path = request.getSession().getServletContext().getRealPath("/")+"static/UserImages";
		request.setCharacterEncoding("UTF-8");
		try {
			return playService.save(file,play,path);
		} catch (Exception e) {
			request.setAttribute("desc", "上传文件过大(限制5M)，或存在异常!");
			return "{\"errorMsg\", \"更新失败\"}";
		}
	}
	
	@ResponseBody
	@RequestMapping("delete")
	public String delete(String delIds) throws IOException {
		return playService.delete(delIds);
	}
	
	@ResponseBody
	@RequestMapping("search")
	public String search(String play_no,String play_noValue) throws IOException {
		return playService.search(play_no, play_noValue);
	}
	
	@ResponseBody
	@RequestMapping("playadd")
	public String playadd(HttpServletRequest request) throws IOException {
		return playService.playadd();
	}
	
	@ResponseBody
	@RequestMapping("saleticket")
	public String saleticket(HttpServletRequest request) throws IOException {
		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String play_name = request.getParameter("PlayName");
		if (play_name == null) {
			play_name = "";
		}
		Play play = new Play();
		play.setPlayName(play_name);
		PageBean pageBean = new PageBean(page, rows);
		return playService.saleticket(pageBean,play);
	}
	
	@ResponseBody
	@RequestMapping("searchByPage")
	public String searchByPage(HttpServletRequest request) throws Exception {

		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String play_name = request.getParameter("PlayName");
		if (play_name == null) {
			play_name = "";
		}
		Play play = new Play();
		play.setPlayName(play_name);
		PageBean pageBean = new PageBean(page, rows);
		return playService.searchByPage(pageBean, play);
	}
}
