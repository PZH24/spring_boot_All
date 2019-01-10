package com.example.spring_boot_mybatis_multidatasouce.imp.mapper1;

import com.example.spring_boot_mybatis_multidatasouce.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//@Mapper
public interface UserMappers1 {
    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);

   UserEntity getUserName(Long id);
}
