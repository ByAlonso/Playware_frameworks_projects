package com.example.playware_final_project;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface Api {
    String BASE_URL = "http://aionso1.pythonanywhere.com";
    @GET("/")
    Call<List<UserModel>> getUser();

    @POST("create")
    Call<UserModel> createPost(@Body UserModel userModel);
}
