package com.micro.service.repository;

import com.micro.service.entity.AuPinEntiry;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Administrator on 2018/5/30.
 */
public interface AuPinRepository extends JpaRepository<AuPinEntiry, String> {
}
