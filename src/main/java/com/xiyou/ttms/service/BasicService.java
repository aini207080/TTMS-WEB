package com.xiyou.ttms.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiyou.ttms.bean.Employee;
import com.xiyou.ttms.bean.User;
import com.xiyou.ttms.dao.EmployeeMapper;
import com.xiyou.ttms.dao.UserMapper;

@Service
public class BasicService {

	@Autowired
	UserMapper userMapper;
	
	@Autowired
	EmployeeMapper employeeMapper;
	
	/*
	 * 验证用户是否存在(即用户名和密码是否正确)
	 */
	public User findUserByNameAndPsw(String empNo,String empPass){

		User user = userMapper.selectByNOandPass(empNo,empPass);
		return user;	
	}
	
	public String ajax(String email,String userName)
    {
        ArrayList<User> userList = (ArrayList<User>) userMapper.selectByExample(null);
        ArrayList<Employee> employeeList = (ArrayList<Employee>) employeeMapper.selectByExample(null);
        String result = "";
        if(email != null && !email.equals(""))
        {
            result = "emailno";
            for(Employee e : employeeList)
            {
                if(e.getEmpEmail().equals(email))
                    result = "emailyes";
            }
            return result;
        }
        if(userName != null && !userName.equals(""))
        {
            result = "userno";
            for(User u : userList)
            {
                if(u.getEmpNo().equals(userName))
                    result = "useryes";
            }
            return result;
        }
        return result;
    }

    public User register(String username, String password)
    {
        User user = new User();
        user.setEmpNo(username);
        user.setEmpPass(password);
        user.setHeadPath("");
        user.setType((byte) 0);
        userMapper.insert(user);
        return user;
    }
}
