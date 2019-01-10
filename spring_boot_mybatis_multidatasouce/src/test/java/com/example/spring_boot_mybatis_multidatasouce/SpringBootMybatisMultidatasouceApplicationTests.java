package com.example.spring_boot_mybatis_multidatasouce;

import com.example.spring_boot_mybatis_multidatasouce.entity.UserEntity;
import com.example.spring_boot_mybatis_multidatasouce.enumer.UserSexEnum;
import com.example.spring_boot_mybatis_multidatasouce.imp.mapper1.UserMappers1;
import com.example.spring_boot_mybatis_multidatasouce.imp.mapper2.UserMappers2;
import com.example.spring_boot_mybatis_multidatasouce.pojo.mapper.UserMapper1;
import com.example.spring_boot_mybatis_multidatasouce.pojo.mapper2.UserMapper2;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootMybatisMultidatasouceApplicationTests {
	@Autowired
	private UserMappers1 userMapper1;
	@Autowired
	private UserMappers2 userMapper2;

	@Test
	public void insertTest1() {
		userMapper1.insert(new UserEntity("aa", "a123456", UserSexEnum.MAN));
		userMapper1.insert(new UserEntity("bb", "b123456", UserSexEnum.WOMAN));
		userMapper1.insert(new UserEntity("cc", "b123456", UserSexEnum.WOMAN));

		Assert.assertEquals(3, userMapper1.getAll().size());
	}
	@Test
	public void insertTest2() {
		userMapper2.insert(new UserEntity("aa2", "a123456", UserSexEnum.MAN));
		userMapper2.insert(new UserEntity("bb2", "b123456", UserSexEnum.WOMAN));
		userMapper2.insert(new UserEntity("cc2", "b123456", UserSexEnum.WOMAN));
	}

}

