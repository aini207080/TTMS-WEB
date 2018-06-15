package com.xiyou.ttms.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.User;
import com.xiyou.ttms.bean.UserExample;
import com.xiyou.ttms.dao.UserMapper;

@Service
public class UserService {

	@Autowired
	UserMapper userMapper;

	public String save(String id,User user){
		int TOF;
		if (id.equals("1")) {
			TOF = userMapper.updateByPrimaryKeySelective(user);
		} else {
			TOF = userMapper.insertSelective(user);
		}
		JsonObject jsobjcet = new JsonObject();
		if (TOF!=0) {
			jsobjcet.addProperty("success", "true");
		} else {
			jsobjcet.addProperty("success", "true");
			jsobjcet.addProperty("errorMsg", "更新失败");
		}
		return jsobjcet.toString();
	}

	// 将所有没头像的用户设置为初始头像
	public String Head(){
		String json = "";
		List<User> f = userMapper.selcetHeadIsNull();
		if (f.size()==0) {
			json = "{\"success\":" + "false" + ",\"errorMsg\":" + "未找到符合的用户" + "}";
			return json;
		}
		int delNums=1;
		for (User u : f) {
			User user = new User();
			user.setEmpNo(u.getEmpNo());
			user.setEmpPass(u.getEmpPass());
			user.setHeadPath("../../static/UserImages/default.jpg");
			user.setType(u.getType());
			delNums = userMapper.updateByPrimaryKeySelective(user);
		}
		if (delNums!=0) {
			json = "{\"success\":" + "true" + ",\"delNums\":" + delNums + "}";
		} else {
			json = "{\"success\":" + "false" + ",\"errorMsg\":" + "设置失败" + "}";
		}
		return json;
	}

	public String delete(String delIds){
		String json = "";
		String str[] = delIds.split(",");
		for (int i = 0; i < str.length; i++) {
			String ss = str[i].substring(1, str[i].length() - 1);
			User f = userMapper.selectByPrimaryKey(ss);
			if (f == null) {
				json = "{\"errorIndex\":" + i + ",\"errorMsg\":" + "未找到符合该编号的用户" + "}";
			}
		}
		UserExample example = new UserExample();
		example.createCriteria().andEmpNoIn(Arrays.asList(str));
		int delNums = userMapper.deleteByExample(example);
		if (delNums > 0) {
			json = "{\"success\":" + "true" + ",\"delNums\":" + delNums + "}";
		} else {
			json = "{\"errorMsg\":" + "删除失败" + "}";
		}
		return json;
	}

	public String searchByPage(PageBean pageBean,User user){
		UserExample example = new UserExample();
		example.createCriteria().andEmpNoLike("%"+user.getEmpNo()+"%");
		long total = userMapper.countByExample(example);
		UserExample example2 = new UserExample();
		example2.createCriteria().andEmpNoLike("%"+user.getEmpNo()+"%");
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		String jsonArray = new Gson().toJson(userMapper.selectByExample(example2));
		String json = "{\"total\":" + total + ",\"rows\":" + jsonArray + "}";
		return json;
	}
}
