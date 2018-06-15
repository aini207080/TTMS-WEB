package com.xiyou.ttms.service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.xiyou.ttms.bean.PageBean;
import com.xiyou.ttms.bean.Seat;
import com.xiyou.ttms.bean.SeatExample;
import com.xiyou.ttms.bean.Studio;
import com.xiyou.ttms.bean.StudioExample;
import com.xiyou.ttms.dao.SeatMapper;
import com.xiyou.ttms.dao.StudioMapper;
import com.xiyou.ttms.utils.StringUtil;

@Service
public class StudioService {
	
	@Autowired
	StudioMapper studioMapper;
	
	@Autowired
	SeatMapper seatMapper;

	public String save(String studio_id,Studio studio)
    {
		int studio_rowINT=studio.getStudioRowCount();
		int studio_colINT=studio.getStudioColCount();
	 	
        int TOF; 
        if(StringUtil.isNotEmpty(studio_id)){
        	Studio studiomodel =studioMapper.selectByPrimaryKey(Integer.parseInt(studio_id));
        	if(studiomodel.getStudioRowCount()==studio_rowINT&&studiomodel.getStudioColCount()==studio_colINT) {
        		
        	}else {
        		SeatExample example = new SeatExample();
        		List<Integer> listIds = Arrays.asList(studio_id.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
        		example.createCriteria().andStudioIdIn(listIds);
        		seatMapper.deleteByExample(example);
        		for(int i=1;i<=studio_rowINT;i++) {
    				for(int j=1;j<=studio_colINT;j++) {
    					Seat seat = new Seat();
    					seat.setStudioId(Integer.parseInt(studio_id));
    					seat.setSeatRow(i);
    					seat.setSeatColumn(j);
    					Short s =1;
    					seat.setSeatStatus(s);
    					seatMapper.insertSelective(seat);
    				}
    			}
        	}
        	TOF=studioMapper.updateByPrimaryKeySelective(studio);
		}else{
			TOF=studioMapper.insertSelective(studio);
			 List<Studio> studioList = studioMapper.selectByExample(null);
			 int studioid = 0;
			 for(Studio s:studioList) {
				 if(s.getStudioId()>studioid)
					 studioid = s.getStudioId();
			 }
			for(int i=1;i<=studio_rowINT;i++) {
				for(int j=1;j<=studio_colINT;j++) {
					Seat seat = new Seat();
					seat.setStudioId(studioid);
					seat.setSeatRow(i);
					seat.setSeatColumn(j);
					Short s =1;
					seat.setSeatStatus(s);
					seatMapper.insertSelective(seat);
				}
			}
		}
        JsonObject jsobjcet = new JsonObject();
        if(TOF!=0){
        	jsobjcet.addProperty("success", "true");
		}else{
			jsobjcet.addProperty("success", "true");
			jsobjcet.addProperty("errorMsg", "¸üÐÂÊ§°Ü"); 
		}
        return jsobjcet.toString();
    }
	
	 public String delete(String delIds)
	    {
	    	String json="";
			String str[]=delIds.split(",");
			for(int i=0;i<str.length;i++){
				Studio f=studioMapper.selectByPrimaryKey(Integer.parseInt(str[i]));
				if(f == null){
					json = "{\"errorIndex\":" + i + ",\"errorMsg\":" + "Î´ÕÒµ½·ûºÏ¸ÃidµÄÑÝ³öÌü" + "}"; 
				}
			}
			SeatExample example = new SeatExample();
    		List<Integer> listIds = Arrays.asList(delIds.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
    		example.createCriteria().andStudioIdIn(listIds);
    		seatMapper.deleteByExample(example);
    		StudioExample example2 = new StudioExample();
    		List<Integer> listIds2 = Arrays.asList(delIds.split(",")).stream().map(s -> Integer.parseInt(s.trim())).collect(Collectors.toList());
    		example2.createCriteria().andStudioIdIn(listIds2);
    		int delNums = studioMapper.deleteByExample(example2);
			if(delNums>0){
				json = "{\"success\":" + "true" + ",\"delNums\":" + delNums + "}"; 
			}else{
				json = "{\"errorMsg\":" + "É¾³ýÊ§°Ü" + "}"; 
			}
			return json;
	    }
	 
	 public String search(String emp_no,String emp_noValue)
	    {
	        List<Studio> studioList = studioMapper.selectByExample(null);
	        String json="";
	        if(emp_no.equals("name"))
	        {
	        	if(emp_noValue!=null)
		        {
	        		for(Studio e : studioList)
	                {
	                    if(e.getStudioName().equals(emp_noValue)) {
	                    	json = "{\"success\":" + false + "}"; 
	                    	break;
	                    }
	                    else
	                    	json = "{\"success\":" + true + "}";
	                }
		        }
	        	
	        }
	        return json;
	    }
	 
	 public String searchByPage(PageBean pageBean,Studio studio)
	    {
			StudioExample example = new StudioExample();
			example.createCriteria().andStudioNameLike("%" +studio.getStudioName()+"%");
			long total = studioMapper.countByExample(example);
			StudioExample example2 = new StudioExample();
			example2.createCriteria().andStudioNameLike("%" +studio.getStudioName()+"%");
			if (pageBean != null) {
				example2.setStart(pageBean.getStart());
				example2.setRows(pageBean.getRows());
			}
			String jsonArray = new Gson().toJson(studioMapper.selectByExample(example2));
			String json = "{\"total\":" + total + ",\"rows\":" + jsonArray + "}"; 
			return json;	
	    }
}
