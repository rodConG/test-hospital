package com.kosmos.test.hospital.repository;

import com.kosmos.test.hospital.model.DoctorsOffice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorsOfficeRepository extends JpaRepository<DoctorsOffice, Long> {
    Optional<DoctorsOffice> findById(Long id);
}
