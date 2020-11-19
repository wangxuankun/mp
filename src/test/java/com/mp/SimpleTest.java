package com.mp;

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
public class SimpleTest {
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Test
	public void select() {
		List<User> user = userMapper.selectList(null);
		
		user.forEach(System.out::println);
		
	}
	
	

}
