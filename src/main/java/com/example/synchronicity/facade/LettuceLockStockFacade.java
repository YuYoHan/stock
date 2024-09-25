package com.example.synchronicity.facade;

import com.example.synchronicity.repository.RedisLockRepository;
import com.example.synchronicity.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LettuceLockStockFacade {
    private final StockService stockService;
    private final RedisLockRepository redisLockRepository;

    public void decreaseStock(Long id, Long quantity) throws InterruptedException {
        while (!redisLockRepository.lock(id)) {
            Thread.sleep(100);
        }

        try {
            stockService.decreaseStock(id, quantity);
        } finally {
            redisLockRepository.unlock(id);
        }
    }
}
