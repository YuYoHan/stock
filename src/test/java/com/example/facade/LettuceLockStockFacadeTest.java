package com.example.facade;

import com.example.synchronicity.facade.LettuceLockStockFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
class LettuceLockStockFacadeTest {
    @Autowired
    private LettuceLockStockFacade lettuceLockStockFacade;

}