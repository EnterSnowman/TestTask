package com.entersnowman.testtask;

import android.app.Activity;
import android.content.Context;

import com.entersnowman.testtask.models.BankExchange;
import com.entersnowman.testtask.models.Exchange;
import com.entersnowman.testtask.models.ExchangeActivity;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Valentin on 18.02.2017.
 */

public class ExchangeTask {
    ExchangeService service;
    Activity activity;
    private ArrayList<BankExchange> nbuList;
    private ArrayList<BankExchange> privatList;
    ExchangeActivity.SectionsPagerAdapter sectionsPagerAdapter;
    public ArrayList<BankExchange> getPrivatList() {
        return privatList;
    }

    public void setPrivatList(ArrayList<BankExchange> privatList) {
        this.privatList = privatList;
    }

    public ArrayList<BankExchange> getNbuList() {
        return nbuList;
    }

    public void setNbuList(ArrayList<BankExchange> nbuList) {
        this.nbuList = nbuList;
    }

    public ExchangeTask(Activity activity){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.privatbank.ua/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.activity = activity;
        service = retrofit.create(ExchangeService.class);
    }

    public Exchange getExchangeByDate(Date date) throws IOException {
        SimpleDateFormat simpleDate =  new SimpleDateFormat("dd.MM.yyyy");
        Call<Exchange> call = service.getExchange(simpleDate.format(date));
        Response<Exchange> response =  call.execute();
        Exchange exchange = response.body();
        BankExchange nbuExchange = new BankExchange();
        BankExchange privatExchange = new BankExchange();
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        try {
            Date d = format.parse(exchange.getDate());
            nbuExchange.setDate(d);
            privatExchange.setDate(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < exchange.getExchangeRate().size(); i++) {
            if (exchange.getExchangeRate().get(i).getCurrency().equals("USD")){
                nbuExchange.setUSDexchange(exchange.getExchangeRate().get(i).getSaleRateNB());
                privatExchange.setUSDexchange(exchange.getExchangeRate().get(i).getSaleRate());
            }
            if (exchange.getExchangeRate().get(i).getCurrency().equals("EUR")){
                nbuExchange.setEURexchange(exchange.getExchangeRate().get(i).getSaleRateNB());
                privatExchange.setEURexchange(exchange.getExchangeRate().get(i).getSaleRate());
            }
            if (exchange.getExchangeRate().get(i).getCurrency().equals("RUR")||exchange.getExchangeRate().get(i).getCurrency().equals("RUB")){
                nbuExchange.setRURexchange(exchange.getExchangeRate().get(i).getSaleRateNB());
                privatExchange.setRURexchange(exchange.getExchangeRate().get(i).getSaleRate());
            }
        }
        nbuList.add(nbuExchange);
        privatList.add(privatExchange);
        System.out.println(response.body().toString());
        return exchange;
    }


    public void setSectionsPagerAdapter(ExchangeActivity.SectionsPagerAdapter sectionsPagerAdapter) {
        this.sectionsPagerAdapter = sectionsPagerAdapter;
    }

}
