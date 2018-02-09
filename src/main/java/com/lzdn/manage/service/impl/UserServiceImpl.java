package com.lzdn.manage.service.impl;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lzdn.manage.domain.core.Dept;
import com.lzdn.manage.domain.core.MenuGroup;
import com.lzdn.manage.domain.core.User;
import com.lzdn.manage.mapper.core.DeptMapper;
import com.lzdn.manage.mapper.core.MenuGroupMapper;
import com.lzdn.manage.mapper.core.UserMapper;
import com.lzdn.manage.service.UserService;
//import com.lzdn.manage.service.redis.RedisService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private DeptMapper deptMapper;

	@Autowired
	private MenuGroupMapper menuGroupMapper;

	@Autowired
	private RedisTemplate redisTemplate;

	/*
	 * @Autowired private RedisTemplate redisTemplate; public void insertUser(User
	 * user) { ValueOperations valueOperations = redisTemplate.opsForValue();
	 * valueOperations.set(USER_PREFIX + user.getId(), user); }
	 * 
	 * 
	 * public User getUser(Integer id) { ValueOperations valueOperations =
	 * redisTemplate.opsForValue();
	 * 
	 * System.out.println(JSON.toJSON(valueOperations.get(USER_PREFIX + id))); User
	 * user = (User) valueOperations.get(USER_PREFIX + id); return user; }
	 */

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userMapper.findAll();
	}

	// 事物测试 如果不指定事物名称 则默认对@Primary 数据库进行事物管理 所以一般最好都指定一下事物名称
	@Override
	@Transactional(value = "coreTransactionManager")
	public int addUser(User user) {
		user.setCreateTime(new Date());
		user.setStatus(1);
		return userMapper.insert(user);
	}

	@Override
	public User login(User user) {
		if (StringUtils.isEmpty(user.getAccount())) {
			return null;
		}
		if (StringUtils.isEmpty(user.getPassword())) {
			return null;
		}
		user = userMapper.login(user);
		if (user == null) {
			return null;
		}
		user = getUserRightByUserId(user.getUserId());
		return user;
	}

	@Override
	public User getUserRightByUserId(Integer userId) {
		ValueOperations<String, User> operations = redisTemplate.opsForValue();
		boolean hasKey = redisTemplate.hasKey("user-info-cache");
		if (hasKey) {
			User user = operations.get("user-info-cache");
			System.out.println(user.toString());
		}
		User user = userMapper.selectByPrimaryKey(userId);
		if (user != null) {
			user = userMapper.getUserRightByUserId(userId);
			Dept dept = deptMapper.selectByPrimaryKey(user.getDeptId());
			user.setDept(dept);
			List<MenuGroup> menuGroups = menuGroupMapper.getMenuGroupByUserId(userId);
			user.setMenuGroups(menuGroups);
			operations.set("user-info-cache", user, 10, TimeUnit.HOURS);
		}
		return user;
	}

}
