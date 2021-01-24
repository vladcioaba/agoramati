package com.agoramati.downloader.vo;

public class PolygonSearchResultVO {
    private Number page;
    private Number perPage;
    private Number count;
    private String status;
    private TickerResultVO[] tickers;

    public Number getPage() {
        return page;
    }

    public void setPage(Number page) {
        this.page = page;
    }

    public Number getPerPage() {
        return perPage;
    }

    public void setPerPage(Number perPage) {
        this.perPage = perPage;
    }

    public Number getCount() {
        return count;
    }

    public void setCount(Number count) {
        this.count = count;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public TickerResultVO[] getTickers() {
        return tickers;
    }

    public void setTickers(TickerResultVO[] tickers) {
        this.tickers = tickers;
    }
}
