package com.lzdn.manage.service;

import java.util.List;

import com.lzdn.manage.domain.center.Product;

public interface ProductService {

	List<Product> findAll();

	int addProduct(Product product);
}
