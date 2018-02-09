package com.lzdn.manage.mapper.center;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.lzdn.manage.domain.center.Product;
import com.lzdn.manage.utils.sql.SqlProvide;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
    
    @SelectProvider(type = SqlProvide.class, method = "getProductAll")
    List<Product> findAll();
}