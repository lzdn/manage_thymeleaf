package com.lzdn.manage.mapper.core;

import java.util.List;

import com.lzdn.manage.domain.core.MenuGroup;

public interface MenuGroupMapper {
    int deleteByPrimaryKey(Integer groupId);

    int insert(MenuGroup record);

    int insertSelective(MenuGroup record);

    MenuGroup selectByPrimaryKey(Integer groupId);

    int updateByPrimaryKeySelective(MenuGroup record);

    int updateByPrimaryKey(MenuGroup record);
    
    List<MenuGroup> getMenuGroupByUserId(Integer userId);
}