package com.agoramati.user.administration.vo;

public class WatchlistResponseVo {
    public String stockId;
    public String stockName;

    public WatchlistResponseVo(String stockId, String stockName) {
        this.stockId = stockId;
        this.stockName = stockName;
    }

    public String getStockId() {
        return stockId;
    }

    public String getStockName() {
        return stockName;
    }
}
