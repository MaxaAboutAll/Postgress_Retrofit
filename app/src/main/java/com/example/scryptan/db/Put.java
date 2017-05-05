package com.example.scryptan.db;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Put {
    @PUT("/")
    Call<ResponseBody> fetchUser(@Query("id") String id, @Query("name") String firstName);
}
