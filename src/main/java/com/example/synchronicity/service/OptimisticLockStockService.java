package com.example.synchronicity.service;

import com.example.synchronicity.entity.Stock;
import com.example.synchronicity.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class OptimisticLockStockService {
    private final StockRepository stockRepository;

    public void decreaseStock(Long id, Long quantity) {
        Stock stock = stockRepository.findByIdWithOptimisticLock(id);
        stock.decreaseStock(quantity);
        stockRepository.save(stock);
    }
}
