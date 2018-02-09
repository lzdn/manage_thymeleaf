package com.lzdn.manage.service;


import java.util.List;

import com.github.pagehelper.Page;
import com.lzdn.manage.domain.core.Dept;

public interface DeptService {

	Page<Dept> findPage(int pageNo, int pageSize);
	
	Dept findByDeptId(Integer deptId);
	
	List<Dept> findAll();
}
