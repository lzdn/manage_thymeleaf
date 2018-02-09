package com.lzdn.manage.mapper.core;

import java.util.List;

import org.apache.ibatis.annotations.SelectProvider;

import com.lzdn.manage.domain.core.User;
import com.lzdn.manage.utils.sql.SqlProvide;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userId);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
	@SelectProvider(type = SqlProvide.class, method = "getUserAll")
	List<User> findAll();

	User login(User record);
	
	User getUserRightByUserId(Integer userId);
}