package com.lzdn.manage.service;

import java.util.List;

import com.lzdn.manage.domain.core.User;

public interface UserService {

	List<User> findAll();

	int addUser(User user);

	User login(User user);

	User getUserRightByUserId(Integer userId);
}
