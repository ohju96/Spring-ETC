package com.example.springdemo.facade;

import com.example.springdemo.service.OptimisticLockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptimisticLockStockFacade {

    private final OptimisticLockService optimisticLockService;

    public void decrease(Long id, Long quantity) throws InterruptedException {

        while (true) {
            try {
                optimisticLockService.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(1);
            }
        }
    }
}
