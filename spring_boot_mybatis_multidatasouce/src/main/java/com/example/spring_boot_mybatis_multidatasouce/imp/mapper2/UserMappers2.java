package com.example.spring_boot_mybatis_multidatasouce.imp.mapper2;

import com.example.spring_boot_mybatis_multidatasouce.entity.UserEntity;

import java.util.List;

//@Mapper
public interface UserMappers2 {
    List<UserEntity> getAll();

    UserEntity getOne(Long id);

    void insert(UserEntity user);

    void update(UserEntity user);

    void delete(Long id);

   UserEntity getUserName(Long id);
}
