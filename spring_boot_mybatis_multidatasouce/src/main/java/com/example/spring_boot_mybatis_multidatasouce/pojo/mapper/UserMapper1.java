package com.example.spring_boot_mybatis_multidatasouce.pojo.mapper;

import com.example.spring_boot_mybatis_multidatasouce.entity.UserEntity;
import com.example.spring_boot_mybatis_multidatasouce.enumer.UserSexEnum;
import org.apache.ibatis.annotations.*;

import java.util.List;
public interface UserMapper1 {
    @Select("select * from users")
    @Results({
            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    List<UserEntity> getAll();

    @Select("SELECT * FROM users WHERE id = #{id}")
    @Results({
            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    UserEntity getOne(Long id);

    @Select("SELECT * FROM users WHERE userName = #{userName}")
    @Results({
            @Result(property = "userSex",  column = "user_sex", javaType = UserSexEnum.class),
            @Result(property = "nickName", column = "nick_name")
    })
    UserEntity findOneByUserName(String userName);

    @Insert("INSERT INTO users (userName,password,user_sex) Values (#{userName},#{passWord},#{userSex})")
    void  insert(UserEntity userEntity);

    @Update("UPDATE users set userName =#{userName},nick_name = #{nickName} WHERE id = #{id}")
    void  update(UserEntity userEntity);

    @Delete("DELETE FROM users where id=#{id}")
    void  delete(long id);
}
