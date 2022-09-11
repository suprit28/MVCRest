package com.spring.projects.MVCRest.repositories;

import com.spring.projects.MVCRest.domain.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}
