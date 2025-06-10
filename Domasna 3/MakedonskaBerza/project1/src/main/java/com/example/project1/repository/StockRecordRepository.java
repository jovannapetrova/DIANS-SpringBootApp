package com.example.project1.repository;

import com.example.project1.model.StockEntity;
import com.example.project1.model.StockRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface StockRecordRepository extends JpaRepository<StockRecordEntity, Long> {
    Optional<StockRecordEntity> findByDateAndCompany(LocalDate date, StockEntity company);
    List<StockRecordEntity> findByCompanyIdAndDateBetween(Long companyId, LocalDate from, LocalDate to);
    List<StockRecordEntity> findAllByDate(LocalDate date);

    List<StockRecordEntity> findByCompanyId(Long companyId);
}


