package com.example.scryptan.db;

public class ApiUtils {

    public static final String BASE_URL = "http://192.168.1.113:3000/";

    public static Post postService() {
        return RetrofitClient.getClient(BASE_URL).create(Post.class);
    }
    public static Get getService() {
        return RetrofitClient.getClient(BASE_URL).create(Get.class);
    }
    public static Delete deleteService() {
        return RetrofitClient.getClient(BASE_URL).create(Delete.class);
    }
    public static Put putService() {
        return RetrofitClient.getClient(BASE_URL).create(Put.class);
    }
}
