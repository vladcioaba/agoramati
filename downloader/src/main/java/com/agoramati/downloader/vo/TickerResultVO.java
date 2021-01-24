package com.agoramati.downloader.vo;

public class TickerResultVO {
    private String ticker;
    private String name;
    private String market;
    private String locale;
    private String currency;
    private Boolean active;
    private String primaryExch;
    private String type;
    private CodesResultVO codes;
    private String updated;
    private String url;

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getPrimaryExch() {
        return primaryExch;
    }

    public void setPrimaryExch(String primaryExch) {
        this.primaryExch = primaryExch;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CodesResultVO getCodes() {
        return codes;
    }

    public void setCodes(CodesResultVO codes) {
        this.codes = codes;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
