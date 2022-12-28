package com.example.springdemo.repository;

import com.example.springdemo.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

// 현업에서는 별도의 JDBC를 사용하는 식으로 해야 한다. 이렇게 바로 stock를 쓰면 안 된다.
public interface LockRepository extends JpaRepository<Stock, Long> {

    @Query(value = "select get_lock(:key, 3000)", nativeQuery = true)
    void getLock(String key);

    @Query(value = "select release_lock(:key)", nativeQuery = true)
    void releaseLock(String key);
}
