package com.mp;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.mp.dao.UserMapper;
import com.mp.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class InsertTest {
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Test
	public void insert() {
		User user = new User();
		user.setAge(20);
		user.setEmail("test.qq.com");
		user.setName("kakaq");
		user.setManagerId(1);
		user.setCreateTime(LocalDateTime.now());
		int c = userMapper.insert(user);
	
		System.out.println(c);
		
		
	}
	
	

}
