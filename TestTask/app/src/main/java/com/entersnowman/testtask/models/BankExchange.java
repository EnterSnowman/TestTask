package com.entersnowman.testtask.models;

import java.util.Date;

/**
 * Created by Valentin on 18.02.2017.
 */

public class BankExchange {
    private Date date;
    private double USDexchange;
    private double EURexchange;
    private double RURexchange;
    public BankExchange(){}

    public BankExchange(Date date, double USDexchange, double EURexchange, double RURexchange) {
        this.date = date;
        this.USDexchange = USDexchange;
        this.EURexchange = EURexchange;
        this.RURexchange = RURexchange;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getUSDexchange() {
        return USDexchange;
    }

    public void setUSDexchange(double USDexchange) {
        this.USDexchange = USDexchange;
    }

    public double getEURexchange() {
        return EURexchange;
    }

    public void setEURexchange(double EURexchange) {
        this.EURexchange = EURexchange;
    }

    public double getRURexchange() {
        return RURexchange;
    }

    public void setRURexchange(double RURexchange) {
        this.RURexchange = RURexchange;
    }
}
