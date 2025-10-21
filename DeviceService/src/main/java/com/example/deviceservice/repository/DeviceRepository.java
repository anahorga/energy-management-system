package com.example.deviceservice.repository;

import com.example.deviceservice.entity.DeviceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DeviceRepository extends JpaRepository<DeviceEntity,Long> {
}