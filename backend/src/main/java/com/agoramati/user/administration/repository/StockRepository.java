package com.agoramati.user.administration.repository;

import com.agoramati.user.administration.model.StockData;
import com.agoramati.user.administration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agoramati.user.administration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<User, Long> {

    @Query("SELECT s FROM StockData s WHERE s.stockId = ?1")
    public StockData getStockData(String stockId);
}