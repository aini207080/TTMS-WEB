package com.xiyou.ttms.dao;

import com.xiyou.ttms.bean.Data_dict;
import com.xiyou.ttms.bean.Data_dictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Data_dictMapper {
    long countByExample(Data_dictExample example);

    int deleteByExample(Data_dictExample example);

    int deleteByPrimaryKey(Integer dictId);

    int insert(Data_dict record);

    int insertSelective(Data_dict record);

    List<Data_dict> selectByExample(Data_dictExample example);

    Data_dict selectByPrimaryKey(Integer dictId);

    int updateByExampleSelective(@Param("record") Data_dict record, @Param("example") Data_dictExample example);

    int updateByExample(@Param("record") Data_dict record, @Param("example") Data_dictExample example);

    int updateByPrimaryKeySelective(Data_dict record);

    int updateByPrimaryKey(Data_dict record);
}