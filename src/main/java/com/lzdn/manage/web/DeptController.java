package com.lzdn.manage.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.Page;
import com.lzdn.manage.domain.core.Dept;
import com.lzdn.manage.service.DeptService;
import com.lzdn.manage.utils.web.PageInfo;
import com.lzdn.manage.web.base.BaseController;

@Controller
@RequestMapping("dept")
public class DeptController extends BaseController {

	@Autowired
	private DeptService deptService;

	@RequestMapping("main")
	public String main(HttpServletRequest request, HttpServletResponse response) {
		return "admin/dept/main";
	}

	@RequestMapping(value = "/{deptId}", method = RequestMethod.GET)
	@ResponseBody
	public Dept get(HttpServletResponse response, @PathVariable Integer deptId) {
		Dept department = deptService.findByDeptId(deptId);
		return department;
	}

	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(HttpServletResponse response, Model model) throws IOException {
		List<Dept> depts = deptService.findAll();
		model.addAttribute("depts", depts);
		return "admin/dept/edit";
	}

	@RequestMapping("find")
	@ResponseBody
	public PageInfo<Dept> findDept(HttpServletRequest request, HttpServletResponse response, int pageNumber,
			int pageSize) {
		Page<Dept> depts = deptService.findPage(pageNumber, pageSize);
		PageInfo<Dept> pageInfo = new PageInfo<>(depts);
		return pageInfo;
	}

	@RequestMapping("delete")
	public void deleteDept(HttpServletRequest request, HttpServletResponse response) {

	}

	@RequestMapping(value = "/edit/{deptId}", method = RequestMethod.GET)
	public String edit(HttpServletResponse response, Model model, @PathVariable String deptId) {
		Dept department = deptService.findByDeptId(new Integer(deptId));
		model.addAttribute("department", department);
		List<Dept> depts = deptService.findAll();
		model.addAttribute("depts", depts);
		return "admin/dept/edit";
	}
}
