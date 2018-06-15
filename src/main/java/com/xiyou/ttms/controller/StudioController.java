package com.xiyou.ttms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Studio;
import com.xiyou.ttms.service.StudioService;
import com.xiyou.ttms.utils.StringUtil;

@Controller
@RequestMapping("studio")
public class StudioController {

	@Autowired
	StudioService studioService;

	@ResponseBody
	@RequestMapping("save")
	public String save(HttpServletRequest request) throws IOException {
		int studio_rowINT = 0;
		int studio_colINT = 0;
		String studio_id = request.getParameter("id");
		String studio_name = request.getParameter("name");
		String studio_row = request.getParameter("row");
		String studio_col = request.getParameter("col");
		String studio_introduction = request.getParameter("introduction");
		String studio_flag = request.getParameter("flag");
		Studio studio = new Studio();
		if (StringUtil.isNotEmpty(studio_id)) {
			studio.setStudioId(Integer.parseInt(studio_id));
		}
		studio.setStudioName(studio_name);
		if (StringUtil.isNotEmpty(studio_row)) {
			studio_rowINT = Integer.parseInt(studio_row);
			studio.setStudioRowCount(studio_rowINT);
		}
		if (StringUtil.isNotEmpty(studio_col)) {
			studio_colINT = Integer.parseInt(studio_col);
			studio.setStudioColCount(studio_colINT);
		}
		studio.setStudioIntroduction(studio_introduction);
		if (StringUtil.isNotEmpty(studio_flag)) {
			studio.setStudioFlag(Short.parseShort(studio_flag));
		}
		return studioService.save(studio_id, studio);
	}

	@ResponseBody
	@RequestMapping("delete")
	public String delete(HttpServletRequest request) throws IOException {
		String delIds = request.getParameter("delIds");
		return studioService.delete(delIds);
	}

	@ResponseBody
	@RequestMapping("search")
	public String search(HttpServletRequest request) throws IOException {
		String emp_no = request.getParameter("fieldId");
		String emp_noValue = request.getParameter("fieldValue");
		return studioService.search(emp_no, emp_noValue);
	}

	@ResponseBody
	@RequestMapping("searchByPage")
	public String searchByPage(HttpServletRequest request) throws Exception {
		int rows = Integer.parseInt(request.getParameter("pageSize"));
		int page = Integer.parseInt(request.getParameter("pageNumber"));
		String studio_name = request.getParameter("studio_name");
		if (studio_name == null) {
			studio_name = "";
		}
		Studio studio = new Studio();
		studio.setStudioName(studio_name);
		PageBean pageBean = new PageBean(page, rows);
		return studioService.searchByPage(pageBean,studio);
	}
}
