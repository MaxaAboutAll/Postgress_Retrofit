package com.example.scryptan.db;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.43.203:3000/";

    public static Post postService() {
        return RetrofitClient.getClient(BASE_URL).create(Post.class);
    }
    public static Get getService() {
        return RetrofitClient.getClient(BASE_URL).create(Get.class);
    }
}
