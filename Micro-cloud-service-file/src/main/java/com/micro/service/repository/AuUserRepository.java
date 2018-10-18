package com.micro.service.repository;

import com.micro.service.entity.AuUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/2/9.
 */
@Repository
public interface AuUserRepository extends JpaRepository<AuUserEntity, String> {
    @Query(value = "SELECT * FROM  au_user WHERE user_name = ?1", nativeQuery = true)
    AuUserEntity  findByusername(String username);
    @Query(value = "SELECT * FROM  au_user WHERE id = ?1", nativeQuery = true)
    AuUserEntity  findByuserid(String id);

}
