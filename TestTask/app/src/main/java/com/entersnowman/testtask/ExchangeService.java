package com.entersnowman.testtask;

import com.entersnowman.testtask.models.Exchange;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Valentin on 18.02.2017.
 */

public interface ExchangeService {
    @GET("p24api/exchange_rates?json")
    Call <Exchange> getExchange(@Query("date") String date);
}
