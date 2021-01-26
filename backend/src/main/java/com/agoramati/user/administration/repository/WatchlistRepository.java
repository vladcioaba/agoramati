package com.agoramati.user.administration.repository;

import com.agoramati.user.administration.model.Watchlist;
import com.agoramati.user.administration.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface WatchlistRepository extends JpaRepository<Watchlist, Long> {
    @Query("SELECT w FROM Watchlist w WHERE w.user.userId = ?1")
    public List<Watchlist> getWatchlistForUser(Long user);

    @Modifying
    @Query(value = "INSERT INTO watchlist_table (user_id, pos, stock_symbol, stock_name) VALUES (:user, 0, :symbol, :name)", nativeQuery = true)
    @Transactional
    public void addSymbol(@Param("user") Long user,
                          @Param("symbol") String symbol,
                          @Param("name") String name);

    @Modifying
    @Query("DELETE FROM Watchlist w WHERE w.user.userId = ?1 and w.stockSymbol = ?2")
    @Transactional
    public void removeSymbol(Long user, String symbol);
}