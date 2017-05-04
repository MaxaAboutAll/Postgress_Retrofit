package com.example.scryptan.db;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Get {
    @GET("/get")
    Call<List<User>> getList();
}
