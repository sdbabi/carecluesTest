package com.careclues.app.service;

import com.careclues.app.model.DataModel;
import com.careclues.app.model.DoctorModel;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("search/physicians")
    Call<List<DataModel>> getDoctorData(@QueryMap Map<String, String> options);

    @GET("search/physicians")
    Call<DoctorModel>getDoctor(@QueryMap Map<String, String> options);

}
