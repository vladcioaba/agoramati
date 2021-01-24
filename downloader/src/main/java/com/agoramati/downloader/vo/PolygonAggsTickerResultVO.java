package com.agoramati.downloader.vo;

public class PolygonAggsTickerResultVO {
    private String ticker;
    private Number queryCount;
    private Number resultsCount;
    private Boolean adjusted;
    private TickerQuoteResultVO[] results;
    private String status;
    private String request_id;
    private Number count;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public Number getQueryCount() {
        return queryCount;
    }

    public void setQueryCount(Number queryCount) {
        this.queryCount = queryCount;
    }

    public Number getResultsCount() {
        return resultsCount;
    }

    public void setResultsCount(Number resultsCount) {
        this.resultsCount = resultsCount;
    }

    public Boolean getAdjusted() {
        return adjusted;
    }

    public void setAdjusted(Boolean adjusted) {
        this.adjusted = adjusted;
    }

    public TickerQuoteResultVO[] getResults() {
        return results;
    }

    public void setResults(TickerQuoteResultVO[] results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }

    public Number getCount() {
        return count;
    }

    public void setCount(Number count) {
        this.count = count;
    }
}

