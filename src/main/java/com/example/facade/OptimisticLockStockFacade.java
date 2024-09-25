package com.example.facade;

import com.example.synchronicity.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OptimisticLockStockFacade {
    private final OptimisticLockStockService optimisticLockStockService;

    public void decreaseStock(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                optimisticLockStockService.decreaseStock(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(50);
            }
        }
    }
}
