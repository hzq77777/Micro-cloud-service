package com.micro.service.repository;

import com.micro.service.entity.AuDeviceEntity;
import com.micro.service.entity.AuUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/2/6.
 */
@Repository
public interface AuDeviceRepository  extends JpaRepository<AuDeviceEntity, String> {
    @Query(value = "SELECT * FROM  au_device WHERE device_name = ?1", nativeQuery = true)
    AuDeviceEntity findBydevicename(String devicename);
    @Query(value = "SELECT * FROM  au_device WHERE id = ?1", nativeQuery = true)
    AuDeviceEntity findBydeviceid(String id);

}
