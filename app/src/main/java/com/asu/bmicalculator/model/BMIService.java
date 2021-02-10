package com.asu.bmicalculator.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BMIService implements ServiceInterface{

    @Override
    public BMIServiceApi createServiceAPI() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BMIServiceApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        BMIServiceApi bmiServiceApi = retrofit.create(BMIServiceApi.class);
        System.out.println("BMI Service API created !");

        return bmiServiceApi;
    }

}
