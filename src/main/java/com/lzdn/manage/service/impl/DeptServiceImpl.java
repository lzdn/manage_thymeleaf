package com.lzdn.manage.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lzdn.manage.domain.core.Dept;
import com.lzdn.manage.mapper.core.DeptMapper;
import com.lzdn.manage.service.DeptService;

@Service
public class DeptServiceImpl implements DeptService {

	@Autowired
	private DeptMapper deptMapper;
	
	@Override
	public Page<Dept> findPage(int pageNo, int pageSize) {
		PageHelper.startPage(pageNo, pageSize);
		return deptMapper.findPage();
	}

	@Override
	public Dept findByDeptId(Integer deptId) {
		// TODO Auto-generated method stub
		return deptMapper.selectByPrimaryKey(deptId);
	}

	@Override
	public List<Dept> findAll() {
		// TODO Auto-generated method stub
		return deptMapper.findAll();
	}

}
