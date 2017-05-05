package com.example.scryptan.db;

import java.util.List;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Post {
    @POST("/")
    Call<ResponseBody> fetchUser(@Query("name") String firstName);
}
