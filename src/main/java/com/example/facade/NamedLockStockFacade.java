package com.example.facade;

import com.example.synchronicity.repository.LockRepository;
import com.example.synchronicity.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class NamedLockStockFacade {
    private final LockRepository lockRepository;
    private final StockService stockService;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseStock(Long id, Long quantity) {
        try {
            lockRepository.getLock(id.toString());
            stockService.decreaseStock(id, quantity);
        } finally {
            lockRepository.releaseLock(id.toString());
        }
    }
}


