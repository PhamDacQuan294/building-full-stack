package com.javaweb.repository.client;

import com.javaweb.entity.ContactRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRequestRepository extends JpaRepository<ContactRequestEntity, Long> {
}