package com.example.project1.service;

import com.example.project1.model.StockRecordEntity;
import com.example.project1.model.StockEntity;
import com.example.project1.repository.StockRecordRepository;
import com.example.project1.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockRecordRepository stockRecordRepository;

    public List<StockEntity> findAll() {
        return stockRepository.findAll();
    }

    public StockEntity findById(Long id) throws Exception {
        return stockRepository.findById(id).orElseThrow(Exception::new);
    }

    public List<StockRecordEntity> findAllToday() {
        return stockRecordRepository.findAllByDate(LocalDate.of(2024,10,10));
    }

}
