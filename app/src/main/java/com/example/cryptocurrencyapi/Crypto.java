package com.example.cryptocurrencyapi;

import java.util.List;

public class Crypto {

    public Ticker ticker;
    public Integer timestamp;
    public Boolean success;
    public String error;

    public class Market {

        public String market;
        public String price;
        public String coinName;
    }

    public class Ticker {

        public String base;
        public String target;
        public String price;
        public String volume;
        public String change;
        public List<Market> markets = null;
    }

    public Ticker getTicker() {
        return ticker;
    }

    public void setTicker(Ticker ticker) {
        this.ticker = ticker;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public Integer getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Integer timestamp) {
        this.timestamp = timestamp;
    }

}