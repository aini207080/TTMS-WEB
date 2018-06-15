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
import com.xiyou.ttms.bean.Employee;
import com.xiyou.ttms.bean.EmployeeExample;
import com.xiyou.ttms.bean.EmployeeExample.Criteria;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.User;
import com.xiyou.ttms.dao.EmployeeMapper;
import com.xiyou.ttms.dao.UserMapper;
import com.xiyou.ttms.utils.StringUtil;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;

	@Autowired
	UserMapper usermapper;

	public String ajax(String emp_no) {
		User user = new User();
		user = usermapper.selectByPrimaryKey(emp_no);
		Employee employee = new Employee();
		employee = employeeMapper.selectByEmpNo(emp_no);
		JsonObject jsobjcet = new JsonObject();
		jsobjcet.addProperty("emp_id", employee.getEmpId());
		jsobjcet.addProperty("emp_no", employee.getEmpNo());
		jsobjcet.addProperty("emp_name", employee.getEmpName());
		jsobjcet.addProperty("emp_tel_num", employee.getEmpTelNum());
		jsobjcet.addProperty("emp_addr", employee.getEmpAddr());
		jsobjcet.addProperty("emp_email", employee.getEmpEmail());
		if (user.getType() == 0)
			jsobjcet.addProperty("type", "user");
		else
			jsobjcet.addProperty("type", "admin");
		return jsobjcet.toString();
	}

	public String save(Employee employee) {
		int TOF;
		if (employee.getEmpId() != null) {
			TOF = employeeMapper.updateByPrimaryKeySelective(employee);
		} else {
			TOF = employeeMapper.insertSelective(employee);
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

	public String passchange(String emp_no, String passwordold, String passwordnew) {
		JsonObject jsobjcet = new JsonObject();
		User ss = new User();
		User user = usermapper.selectByPrimaryKey(emp_no);
		if (user.getEmpPass().equals(passwordold)) {
			ss.setEmpNo(emp_no);
			ss.setEmpPass(passwordnew);
			ss.setType(user.getType());
			ss.setHeadPath(user.getHeadPath());
			usermapper.updateByPrimaryKeySelective(ss);
			jsobjcet.addProperty("success", true);
		} else {
			jsobjcet.addProperty("success", false);
			jsobjcet.addProperty("errorMsg", "原密码错误，更改失败");
		}
		return jsobjcet.toString();
	}

	public String delete(String delIds) {
		String json = "";
		String str[] = delIds.split(",");
		
		for (int i = 0; i < str.length; i++) {
			Employee f = employeeMapper.selectByPrimaryKey(Integer.parseInt(str[i]));
			if (f == null) {
				json = "{\"errorIndex\":" + i + ",\"errorMsg\":" + "未找到符合该id的员工" + "}";
				return json;
			}
		}
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		List<Integer> listIds = Arrays.asList(delIds.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());  
		criteria.andEmpIdIn(listIds);
		int delNums = employeeMapper.deleteByExample(example);
		if (delNums > 0) {
			json = "{\"success\":" + "true" + ",\"delNums\":" + delNums + "}";
		} else {
			json = "{\"errorMsg\":" + "删除失败" + "}";
		}
		return json;
	}

	public void updateMyprofile(MultipartFile file, Employee employee1,String path) throws IOException {
		User user = new User();
		employeeMapper.updateByPrimaryKeySelective(employee1);
		String fileName = file.getOriginalFilename();
		File dir = new File(path, fileName);
		if(!dir.exists()){  
            dir.mkdirs();  
        }
		file.transferTo(dir);
		user.setEmpNo(employee1.getEmpNo());
		user.setHeadPath("../../static/UserImages/" + fileName);
		user.setEmpPass(usermapper.selectByPrimaryKey(employee1.getEmpNo()).getEmpPass());
		user.setType(usermapper.selectByPrimaryKey(employee1.getEmpNo()).getType());
		usermapper.updateByPrimaryKeySelective(user);
	}


	public String useradd() {
		String json = "";
		List<Employee> e = employeeMapper.selectByNotInUser();
		for (Employee s : e) {
			json += s.getEmpNo() + ",";
		}
		return json;
	}

	public String search(String emp_no, String emp_noValue) {
		List<Employee> employeeList = employeeMapper.selectByExample(null);
		String json = "";
		if (emp_no.equals("number")) {
			if (emp_noValue != null) {
				for (Employee e : employeeList) {
					if (e.getEmpNo().equals(emp_noValue)) {
						json = "{\"success\":" + false + "}";
						break;
					} else
						json = "{\"success\":" + true + "}";
				}
			}

		} else if (emp_no.equals("phone")) {
			if (emp_noValue != null) {
				for (Employee e : employeeList) {
					if (e.getEmpTelNum().equals(emp_noValue)) {
						json = "{\"success\":" + false + "}";
						break;
					} else
						json = "{\"success\":" + true + "}";
				}
			}
		} else if (emp_no.equals("email")) {
			if (emp_noValue != null) {
				for (Employee e : employeeList) {
					if (e.getEmpEmail().equals(emp_noValue)) {
						json = "{\"success\":" + false + "}";
						break;
					} else
						json = "{\"success\":" + true + "}";
				}
			}
		}
		return json;
	}

	public String searchByPage(PageBean pageBean, Employee employee){
		EmployeeExample example = new EmployeeExample();
		Criteria criteria = example.createCriteria();
		if (StringUtil.isNotEmpty(employee.getEmpName())) {
			criteria.andEmpNameLike("%" + employee.getEmpName() + "%");
		}
		long total = employeeMapper.countByExample(example);

		EmployeeExample example2 = new EmployeeExample();
		Criteria criteria2 = example2.createCriteria();
		if (employee != null && StringUtil.isNotEmpty(employee.getEmpName())) {
			criteria2.andEmpNameLike("%" + employee.getEmpName() + "%");
		}
		if (pageBean != null) {
			example2.setStart(pageBean.getStart());
			example2.setRows(pageBean.getRows());
		}
		String jsonArray = new Gson().toJson(employeeMapper.selectByExample(example2));
		String json = "{\"total\":" + total + ",\"rows\":" + jsonArray + "}";
		return json;
	}
}
