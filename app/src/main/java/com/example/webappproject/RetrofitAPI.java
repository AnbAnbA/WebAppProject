package com.example.webappproject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

import retrofit2.http.DELETE;
import retrofit2.http.Query;

import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface RetrofitAPI {
    @POST("Medicines")
    Call<Med> createPost(@Body Med dataModal);

    @DELETE("Medicines/")
    Call<Med> createDelete(@Query("id") int id);

    @PUT("Medicines/")
    Call<Med> createPut(@Body Med dataModal, @Query("ID") int id);
}
