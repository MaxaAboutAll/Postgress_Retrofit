package com.example.scryptan.db;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Query;

/**
 * Created by ScryptAn on 05.05.2017.
 */

public interface Delete {
    @DELETE("/")
    Call<ResponseBody> fetchUser(@Query("id") String id);
}
