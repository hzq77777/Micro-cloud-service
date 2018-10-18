package com.micro.service.repository;

import com.micro.service.entity.AuUserDeviceEntity;
import com.micro.service.entity.AuUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/3/1.
 */
@Repository
public interface AuUserDeviceRepository extends JpaRepository<AuUserDeviceEntity, String> {
    @Query(value = "SELECT * FROM  au_user_device WHERE id = ?1", nativeQuery = true)
    AuUserDeviceEntity findByid(Integer id);

    @Query(value = "SELECT * FROM  au_user_device WHERE username = ?1", nativeQuery = true)
    AuUserDeviceEntity findByusername(String name);

    @Query(value = "SELECT * FROM  au_user_device WHERE devicename = ?1", nativeQuery = true)
    AuUserDeviceEntity findBydevicename(String name);
}
