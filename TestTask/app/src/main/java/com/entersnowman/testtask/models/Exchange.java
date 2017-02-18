
package com.entersnowman.testtask.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Exchange implements Serializable
{

    @SerializedName("date")
    //@Expose
    private String date;
    @SerializedName("bank")
    //@Expose
    private String bank;
    @SerializedName("baseCurrency")
    //@Expose
    private Integer baseCurrency;
    @SerializedName("baseCurrencyLit")
    //@Expose
    private String baseCurrencyLit;
    @SerializedName("exchangeRate")
    //@Expose
    private List<ExchangeRate> exchangeRate = new ArrayList<ExchangeRate>();
    private final static long serialVersionUID = -4559210558366897774L;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public Integer getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(Integer baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    public void setBaseCurrencyLit(String baseCurrencyLit) {
        this.baseCurrencyLit = baseCurrencyLit;
    }

    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(List<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }


    @Override
    public String toString() {
        return "Exchange{" +
                "date='" + date + '\'' +
                ", bank='" + bank + '\'' +
                ", baseCurrency=" + baseCurrency +
                ", baseCurrencyLit='" + baseCurrencyLit + '\'' +
                ", exchangeRate=" + exchangeRate.size() +
                '}';
    }
}