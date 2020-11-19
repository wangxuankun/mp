package com.mp;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.mp.dao.UserMapper;
import com.mp.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class UpdteTest {
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Test
	public void update() {
		User user = new User();
		user.setId(1L);
		user.setAge(20);
		int c = userMapper.updateById(user);
		System.out.println(c);
	}
	
	@Test
	public void updateByWrapper() {
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
		updateWrapper.eq("name", "a").eq("age", 20);
		User user = new User();
		user.setEmail("a.xiaomi.com");
		int c = userMapper.update(user, updateWrapper);
		System.out.println(c);
	}
	
	@Test
	public void updateByWrapper2() {
		User whereUser = new User();
		whereUser.setAge(20);
		whereUser.setName("a");
		
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>(whereUser);
		User user = new User();
		user.setEmail("a.xiaomi.com");
		int c = userMapper.update(user, updateWrapper);
		System.out.println(c);
	}
	
	@Test
	public void updateByWrapper3() {
		
		UpdateWrapper<User> updateWrapper = new UpdateWrapper<User>();
		updateWrapper.eq("name", "a").eq("age", 20).set("age", 21);
		int c = userMapper.update(null, updateWrapper);
		System.out.println(c);
	}
	
	@Test
	public void updateByWrapperLambda() {
		
		LambdaUpdateWrapper<User> lambdaUpdate = Wrappers.<User>lambdaUpdate();
		lambdaUpdate.eq(User::getName, "a").eq(User::getAge, 20).set(User::getAge, 21);
		int c = userMapper.update(null, lambdaUpdate);
		System.out.println(c);
	}
	
	@Test
	public void updateByWrapperLambdaChain() {
		
		boolean update = new LambdaUpdateChainWrapper<User>(userMapper)
				.eq(User::getName, "a")
				.eq(User::getAge, 20).set(User::getAge, 21).update();
		System.out.println(update);
	}
	

}
