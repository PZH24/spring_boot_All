package com.example.spring_boot_mybatis.imp;

import com.example.spring_boot_mybatis.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

//@Mapper
public interface UserMappers {
    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);

   UserEntity getUserName(Long id);
}
