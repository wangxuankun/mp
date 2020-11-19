package com.mp;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.mp.dao.UserMapper;
import com.mp.entity.User;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Search {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void selectById() {

		User user = userMapper.selectById(1);
		System.out.println(user);

	}

	@Test
	public void selectIds() {
		List<Long> idsList = Arrays.asList(1328288992118042626L, 1L);

		List<User> user = userMapper.selectBatchIds(idsList);
		user.forEach(System.out::println);

	}

	@Test
	public void selectByMap() {
		Map<String, Object> map = new HashMap<>();
		map.put("name", "a");
		map.put("email", "a.qq.com");
		List<User> user = userMapper.selectByMap(map);
		user.forEach(System.out::println);

	}

	@Test
	public void selectByWrapper() {
		// 名字中包含a并且年龄少于40
		// QueryWrapper<User> query = Wrappers.<User>query();
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>();
		queryWrapper.lt("age", 40).like("name", "a");
		List<User> user = userMapper.selectList(queryWrapper);
		user.forEach(System.out::println);
	}

	@Test
	public void selectByWrapper2() {
		// 名字中包含a并且年龄20 <= age <= 40 and email 不为空
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.between("age", 10, 40).like("name", "a").isNotNull("email");
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrapper3() {
		// 名字中包含a 或者 年龄大于等于25，按照年龄降序排序,年龄相同按照id生序排列,
		// name like 'a%' or age >= 40 order by age desc ,id asc
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.likeRight("name", "a").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrapper4() {
		// 创建日期为2019年2月14日并且直属上级为名字为王姓
		// date(create_time,'%Y-%m-%d') and manager_id in (select id from user where
		// name like 'a%')
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.apply("date_format(create_time,'%Y-%m-%d') = {0}", "2019-02-14").inSql("manager_id",
				"select id from user where name like 'a%'");
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrapper5() {
		// 名字为a 并且 （年龄小于40 或 邮箱不为空）
		// name like 'a%' and (age < 40 or email is not null)
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.likeRight("name", "a").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrappe6() {
		// 名字为a 或者 （年龄小于40 并且 年龄大于 20 并且 邮箱不为空）
		// name like 'a%' or ( age < 40 and age > 20 and email is not null)
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.likeRight("name", "a").or(wq -> wq.gt("age", 40).gt("age", 20).isNotNull("email"));
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrappe7() {
		// （年龄小于40 或 邮箱不为空） 并且 名字为a
		// ( age < 40 or email is not null ） and name like 'a%'
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.nested(wq -> wq.lt("age", 40).or().isNotNull("email")).like("name", "a");
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrappe8() {
		// age in(30,31,34,35)
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.in("age", Arrays.asList(30, 31, 32, 34, 35));
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrappe9() {
		// age in(30,31,34,35) limit 1
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.select("name").in("age", Arrays.asList(25, 31, 32, 34, 35)).last("limit 1");
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrappe10() {
		// age in(30,31,34,35) limit 1
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper
				.select(User.class,
						info -> !info.getColumn().equals("create_time") && !info.getColumn().equals("manager_id"))
				.in("age", Arrays.asList(25, 31, 32, 34, 35)).last("limit 1");
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void testCondition() {
		String name = "a";
		String email = "";
		condition(name, email);
	}

	private void condition(String name, String email) {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		/*
		 * if(StringUtils.isNotEmpty(name)) { queryWrapper.like("name", name); }
		 * if(StringUtils.isNotEmpty(email)) { queryWrapper.like("email", email); }
		 */
		queryWrapper.like(StringUtils.isNotEmpty(name), "name", name).like(StringUtils.isNotEmpty(email), "email",
				email);

		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);

	}

	@Test
	public void selectByWrappeEntity() {

		User user = new User();
		user.setName("a");
		user.setAge(20);
		QueryWrapper<User> queryWrapper = Wrappers.<User>query(user); // 直接用实体查询
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrappeAllEq() {

		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", "a");
		map.put("age", 20);
		map.put("email", null);
		// queryWrapper.allEq(map);
		// queryWrapper.allEq(map ,false); //null 忽律掉
		queryWrapper.allEq((k, v) -> !k.equals("name"), map); // 去掉name 字段条件
		List<User> user2 = userMapper.selectList(queryWrapper);
		user2.forEach(System.out::println);
	}

	// 返回固定字段或统计查询使用
	@Test
	public void selectByWrappeMaps() {

		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.select("id", "name").likeRight("name", "a")
				.or(wq -> wq.gt("age", 40).gt("age", 20).isNotNull("email"));
		List<Map<String, Object>> user2 = userMapper.selectMaps(queryWrapper);
		user2.forEach(System.out::println);
	}

	@Test
	public void selectByWrappeMaps2() {
		// select avg(age) avg_age,min(age)min_age,max(age) max_age from user group by
		// manager_id having sum(age) < 500
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.select("avg(age) avg_age", "min(age) min_age", "max(age) max_age").groupBy("manager_id")
				.having("sum(age) < {0}", 500);
		List<Map<String, Object>> user2 = userMapper.selectMaps(queryWrapper);
		user2.forEach(System.out::println);
	}

	// 只返回一列值
	@Test
	public void selectByWrappeObjs() {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.select("id", "name").isNotNull("email");
		List<Object> user2 = userMapper.selectObjs(queryWrapper);
		user2.forEach(System.out::println);
	}

	// count(*)
	@Test
	public void selectByWrappeCount() {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.isNotNull("email");
		Integer c = userMapper.selectCount(queryWrapper);
		System.out.println(c);
	}

	// 只能查询返回一条的，多条会报错
	@Test
	public void selectByWrappeOne() {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.isNotNull("email").eq("name", "a");
		User user = userMapper.selectOne(queryWrapper);
		System.out.println(user);
	}

	@Test
	public void selectLambda() {
		// LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
		// LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<User>();
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
		lambdaQuery.like(User::getName, "a").lt(User::getAge, 40);
		List<User> user2 = userMapper.selectList(lambdaQuery);
		user2.forEach(System.out::println);

	}

	@Test
	public void selectLambda2() {
		// SELECT id,name,email,age,manager_id,create_time FROM user WHERE name LIKE ?
		// AND ( age < ? OR email IS NOT NULL )
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
		lambdaQuery.likeRight(User::getName, "a").and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
		List<User> user2 = userMapper.selectList(lambdaQuery);
		user2.forEach(System.out::println);

	}

	@Test
	public void selectLambda3() {
		// SELECT id,name,email,age,manager_id,create_time FROM user WHERE name LIKE ?
		// AND ( age < ? OR email IS NOT NULL )
		List<User> user2 = new LambdaQueryChainWrapper<User>(userMapper).likeRight(User::getName, "a")
				.and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail)).list();
		user2.forEach(System.out::println);

	}

	@Test
	public void selectMy() {
		LambdaQueryWrapper<User> lambdaQuery = Wrappers.<User>lambdaQuery();
		lambdaQuery.likeRight(User::getName, "a").and(lqw -> lqw.lt(User::getAge, 40).or().isNotNull(User::getEmail));
		List<User> user2 = userMapper.selectAll(lambdaQuery);
		user2.forEach(System.out::println);

	}

	//返回实体类型的结果
	@Test
	public void selectPage() {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.ge("age", 20);
		Page<User> page = new Page<User>(1, 2);
		IPage<User> ipage = userMapper.selectPage(page, queryWrapper);
		System.out.println("总页数" + ipage.getPages());
		System.out.println("总记录数" + ipage.getTotal());
		List<User> userList = ipage.getRecords();
		userList.forEach(System.out::println);
	}
	
	//返回map类型的结果
	@Test
	public void selectPage2() {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.ge("age", 20);
		Page<User> page = new Page<User>(1, 2);
		IPage<Map<String, Object>> ipage = userMapper.selectMapsPage(page, queryWrapper);
		System.out.println("总页数" + ipage.getPages());
		System.out.println("总记录数" + ipage.getTotal());
		List<Map<String, Object>> userList = ipage.getRecords();
		userList.forEach(System.out::println);
	}
	
	//不需要记录总数，每次翻页就可以
	@Test
	public void selectPage3() {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.ge("age", 20);
		Page<User> page = new Page<User>(1, 2,false); //不查询总记录数量
		IPage<Map<String, Object>> ipage = userMapper.selectMapsPage(page, queryWrapper);
		System.out.println("总页数" + ipage.getPages());
		System.out.println("总记录数" + ipage.getTotal());
		List<Map<String, Object>> userList = ipage.getRecords();
		userList.forEach(System.out::println);
	}
	
	@Test
	public void selectMyPage() {
		QueryWrapper<User> queryWrapper = Wrappers.<User>query();
		queryWrapper.ge("age", 20);
		Page<User> page = new Page<User>(1, 2);
		IPage<User> ipage = userMapper.selectUserPage(page, queryWrapper);
		System.out.println("总页数" + ipage.getPages());
		System.out.println("总记录数" + ipage.getTotal());
		List<User> userList = ipage.getRecords();
		userList.forEach(System.out::println);

	}

}
