package com.lzdn.manage.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzdn.manage.domain.center.Product;
import com.lzdn.manage.mapper.center.ProductMapper;
import com.lzdn.manage.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductMapper productMapper;

	@Override
	public List<Product> findAll() {
		// TODO Auto-generated method stub
		return productMapper.findAll();
	}

	@Override
	@Transactional(value="centerTransactionManager")
	public int addProduct(Product product) {
		int i = productMapper.insert(product);
		i = i /0;
		return i;
	}

}
