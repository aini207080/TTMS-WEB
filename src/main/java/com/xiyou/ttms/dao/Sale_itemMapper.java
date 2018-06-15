package com.xiyou.ttms.dao;

import com.xiyou.ttms.bean.Sale_item;
import com.xiyou.ttms.bean.Sale_itemExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Sale_itemMapper {
    long countByExample(Sale_itemExample example);

    int deleteByExample(Sale_itemExample example);

    int deleteByPrimaryKey(Long saleItemId);

    int insert(Sale_item record);

    int insertSelective(Sale_item record);

    List<Sale_item> selectByExample(Sale_itemExample example);

    Sale_item selectByPrimaryKey(Long saleItemId);

    int updateByExampleSelective(@Param("record") Sale_item record, @Param("example") Sale_itemExample example);

    int updateByExample(@Param("record") Sale_item record, @Param("example") Sale_itemExample example);

    int updateByPrimaryKeySelective(Sale_item record);

    int updateByPrimaryKey(Sale_item record);
}