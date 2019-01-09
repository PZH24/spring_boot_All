package com.example.spring_boot_mybatis;

import com.example.spring_boot_mybatis.entity.UserEntity;
import com.example.spring_boot_mybatis.enumer.UserSexEnum;
import com.example.spring_boot_mybatis.imp.UserMappers;
import com.example.spring_boot_mybatis.pojo.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisApplicationTests {

	@Autowired
	private UserMappers userMapper;
	@Test
	public void contextLoads() {

	}
	@Test
	public void insertTest()  throws  Exception{
		userMapper.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		userMapper.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		userMapper.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

		Assert.assertEquals(3, userMapper.getAll().size());
	}
	@Test
	public void testQuery() throws Exception {
		List<UserEntity> users = userMapper.getAll();
		System.out.println(users.toString());
	}

	@Test
	public void testUpdate() throws Exception {
		UserEntity user = userMapper.getOne(3L);
//		UserEntity user = userMapper.findOneByUserName("bb");
//		List<UserEntity> users = userMapper.getAll();
//		UserEntity user =users.stream().filter(userEntity -> userEntity.getId()==31).collect(Collectors.toList()).get(0);
		System.out.println(user.toString());
		user.setNickName("neo");
		userMapper.update(user);
		Assert.assertTrue(("neo".equals(userMapper.getOne(3L).getNickName())));
	}
	@Test
	public  void  getOneTest() throws Exception {
		UserEntity user = userMapper.getOne(3L);
		System.out.println(user.toString());
	}
	@Test
	public  void  getUserNameTest(){
		UserEntity userName = userMapper.getUserName(3L);
		Assert.assertTrue("bb".equalsIgnoreCase(userName.getUserName()));
	}
}

