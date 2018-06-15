package com.xiyou.ttms.service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiyou.ttms.bean.Data_dict;
import com.xiyou.ttms.bean.Data_dictExample;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Play;
import com.xiyou.ttms.bean.PlayExample;
import com.xiyou.ttms.bean.PlayExample.Criteria;
import com.xiyou.ttms.dao.Data_dictMapper;
import com.xiyou.ttms.dao.PlayMapper;
import com.xiyou.ttms.utils.StringUtil;

@Service
public class PlayService {

	@Autowired
	PlayMapper playMapper;
	
	@Autowired
	Data_dictMapper data_dictMapper;

	public String save(MultipartFile file,Play play,String path){
		System.out.println("!!!!!");
		String fileName = file.getOriginalFilename();
		File dir = new File(path, fileName);
		if(!dir.exists()){  
            dir.mkdirs();  
        }
		try {
			file.transferTo(dir);
		} catch (IllegalStateException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		play.setPlayImage("../../static/UserImages/" + fileName);
		int TOF;
		if (play.getPlayId() != null) {
			TOF =   playMapper.updateByPrimaryKeySelective(play);
		} else {
			TOF = playMapper.insertSelective(play);
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
			Play f = playMapper.selectByPrimaryKey(Integer.parseInt(str[i]));
			if (f == null) {
				json = "{\"errorIndex\":" + i + ",\"errorMsg\":" + "未找到符合该id的员工" + "}";
				return json;
			}
		}
		PlayExample example = new PlayExample();
		List<Integer> listIds = Arrays.asList(delIds.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());  
		example.createCriteria().andPlayIdIn(listIds);
		int delNums = playMapper.deleteByExample(example);
		if (delNums > 0) {
			json = "{\"success\":" + "true" + ",\"delNums\":" + delNums + "}";
		} else {
			json = "{\"errorMsg\":" + "删除失败" + "}";
		}
		return json;
	}
	
	public String searchByPage(PageBean pageBean, Play play){
		PlayExample example = new PlayExample();
		if (StringUtil.isNotEmpty(play.getPlayName())) {
			example.createCriteria().andPlayNameLike("%" + play.getPlayName() + "%");
		}
		long total = playMapper.countByExample(example);

		PlayExample example2 = new PlayExample();
		if (play != null && StringUtil.isNotEmpty(play.getPlayName())) {
			example2.createCriteria().andPlayNameLike("%" + play.getPlayName() + "%");
		}
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		String jsonArray = new Gson().toJson(playMapper.selectByExample(example2));
		String json = "{\"total\":" + total + ",\"rows\":" + jsonArray + "}";
		return json;
	}

	public String search(String play_no, String play_noValue) {
		
		List<Play> playList = playMapper.selectByExample(null);
		String json = "";
		if (play_no.equals("playName")) {
			if (play_noValue != null) {
				for (Play e : playList) {
					if (e.getPlayName().equals(play_noValue)) {
						json = "{\"success\":" + false + "}";
						break;
					} else
						json = "{\"success\":" + true + "}";
				}
			}

		}
		return json;
	}

	public String playadd() {

		String s1 = "";
		String s2 = "";
		String p1 = "";
		String p2 = "";
		Data_dictExample example = new Data_dictExample();
		example.createCriteria().andDictParentIdEqualTo(2);
		List<Data_dict> data_dicts = data_dictMapper.selectByExample(example);
		for (Data_dict d : data_dicts) {
			s1 += d.getDictId() + ",";
			s2 += d.getDictValue() + ",";
		}
		Data_dictExample example2 = new Data_dictExample();
		example2.createCriteria().andDictParentIdEqualTo(3);
		List<Data_dict> data_dicts2 = data_dictMapper.selectByExample(example2);
		for (Data_dict d : data_dicts2) {
			p1 += d.getDictId() + ",";
			p2 += d.getDictValue() + ",";
		}
		return s1+"|"+s2+"|"+p1+"|"+p2;
	}

	public String saleticket(PageBean pageBean, Play play) {

		PlayExample example = new PlayExample();
		Criteria critera = example.createCriteria();
		critera.andPlayStatusEqualTo((short) 1);
		if (StringUtil.isNotEmpty(play.getPlayName())) {
			critera.andPlayNameLike("%" + play.getPlayName() + "%");
		}
		long total = playMapper.countByExample(example);

		PlayExample example2 = new PlayExample();
		Criteria critera2 = example2.createCriteria();
		critera2.andPlayStatusEqualTo((short) 1);
		if (play != null && StringUtil.isNotEmpty(play.getPlayName())) {
			critera2.andPlayNameLike("%" + play.getPlayName() + "%");
		}
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		String jsonArray = new Gson().toJson(playMapper.selectByExample(example2));
		String json = "{\"total\":" + total + ",\"rows\":" + jsonArray + "}";
		return json;
	}
	
}
