package com.asu.bmicalculator.model;

import com.asu.bmicalculator.entity.BMIResponseEntity;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BMIServiceApi {
    String BASE_URL = "http://webstrar99.fulton.asu.edu";

    @GET("/page3/Service1.svc/calculateBMI")
    Call<BMIResponseEntity> getBMI(@Query("height") double height, @Query("weight") double weight);

}
