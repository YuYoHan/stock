package com.example.facade;

import com.example.synchronicity.entity.Stock;
import com.example.synchronicity.facade.OptimisticLockStockFacade;
import com.example.synchronicity.repository.StockRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest
class OptimisticLockStockFacadeTest {
    @Autowired
    private OptimisticLockStockFacade optimisticLockStockFacade;
    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void before() {
        Stock stock = Stock.builder()
                .productId(1L)
                .quantity(100L)
                .build();
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    void after() {
        stockRepository.deleteAll();
    }


    @Test
    void 동시에_100개_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    optimisticLockStockFacade.decreaseStock(1L, 1L);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    // 다른 스레드에서 작업이 완료할 때까지 대기하는 메서드
                    latch.countDown();
                }
            });
        }
        latch.await();
        Stock stock = stockRepository.findById(1L).orElseThrow();
        // 100 - (1* 100) = 0
        Assertions.assertThat(stock.getQuantity()).isEqualTo(0);
    }
}