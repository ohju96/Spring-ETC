package com.example.springdemo.service;

import com.example.springdemo.domain.Stock;
import com.example.springdemo.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    // synchronized 해당 메소드에 1개의 스레드만 접근할 수 있게 한다.
//    @Transactional기 (Synchronized사용 시 주석 처리 필수)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public synchronized void decrease(Long id, Long quantity) {

        Stock stock = stockRepository.findById(id).orElseThrow();

        stock.decrease(quantity);

        stockRepository.saveAndFlush(stock);
    }
}
