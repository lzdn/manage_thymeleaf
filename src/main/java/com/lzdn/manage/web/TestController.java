package com.lzdn.manage.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.lzdn.manage.domain.center.Product;
import com.lzdn.manage.domain.core.User;
import com.lzdn.manage.service.ProductService;
import com.lzdn.manage.service.UserService;

@RestController
public class TestController {

	@Autowired
	private UserService userService;
	@Autowired
	private ProductService productService;

	@RequestMapping("sayHello")
	public String sayHello() {
		return "hello world";
	}

	@RequestMapping("addUser")
	public String addUser() {
		User user = new User();
		user.setUsername("zhanglin");
		userService.addUser(user);
		return "yes";
	}

	@RequestMapping("addProduct")
	public String addProduct() {
		Product pro = new Product();
		pro.setProductName("苹果8");
		productService.addProduct(pro);
		return "yes";
	}
	
	@RequestMapping("getUsers")
	public List<User> getUsers() {
		return userService.findAll();
	}
	
	@RequestMapping("getProducts")
	public List<Product> getProducts() {
		List<Product> pros =  productService.findAll();
		
		return pros;
	}
	
	
}
