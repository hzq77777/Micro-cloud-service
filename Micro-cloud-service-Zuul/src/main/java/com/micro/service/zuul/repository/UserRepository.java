package com.micro.service.zuul.repository;

import com.micro.service.zuul.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<UserEntity,Long>
{
    //使用SpringDataJPA方法定义查询
    public UserEntity findByUsername(String username);
}

