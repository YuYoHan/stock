package com.example.synchronicity.service;

import com.example.synchronicity.entity.Stock;
import com.example.synchronicity.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;


    // 재고 감소
    public synchronized void decreaseStock(Long id, Long quantity) {
        // stock 조회
        // 재고 감소 후 갱신된 값을 저장
        Stock stock = stockRepository.findById(id)
                .orElseThrow();
        stock.decreaseStock(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
