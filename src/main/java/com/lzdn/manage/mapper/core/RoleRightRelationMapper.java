package com.lzdn.manage.mapper.core;

import com.lzdn.manage.domain.core.RoleRightRelation;

public interface RoleRightRelationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleRightRelation record);

    int insertSelective(RoleRightRelation record);

    RoleRightRelation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleRightRelation record);

    int updateByPrimaryKey(RoleRightRelation record);
}