package com.lzdn.manage.mapper.core;


import java.util.List;

import com.github.pagehelper.Page;
import com.lzdn.manage.domain.core.Dept;

public interface DeptMapper {
    int deleteByPrimaryKey(Integer deptId);

    int insert(Dept record);

    int insertSelective(Dept record);

    Dept selectByPrimaryKey(Integer deptId);

    int updateByPrimaryKeySelective(Dept record);

    int updateByPrimaryKey(Dept record);
    
    Page<Dept> findPage();
    
    List<Dept> findAll();
}