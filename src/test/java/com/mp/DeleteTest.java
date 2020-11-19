package com.mp;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
import com.mp.dao.UserMapper;
import com.mp.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DeleteTest {
	
	@Autowired
	private UserMapper userMapper;
	
	
	@Test
	public void deleteById() {
		int c = userMapper.deleteById(1328293665709236226L);
		System.out.println(c);
	}
	
	

}
