package com.example.scryptan.db;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Post {
    @POST("/")
    Call<List<User>> fetchUser(@Query("name") String firstName);
}
