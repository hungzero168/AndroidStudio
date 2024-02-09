package com.example.foodapp.Api;

import com.example.foodapp.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    Gson gson = new GsonBuilder()
            .setDateFormat("yyy-MM-dd HH:mm:ss").create();


    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost/FoodApp/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    @GET("/apiUser.php")
    Call<List<User>> getUser(@Query("taikhoan") String taikhoan,
                       @Query("matkhau") String matkhau);

}
