package com.lzdn.manage.mapper.core;

import com.lzdn.manage.domain.core.Right;

public interface RightMapper {
    int deleteByPrimaryKey(Integer rightId);

    int insert(Right record);

    int insertSelective(Right record);

    Right selectByPrimaryKey(Integer rightId);

    int updateByPrimaryKeySelective(Right record);

    int updateByPrimaryKey(Right record);
}