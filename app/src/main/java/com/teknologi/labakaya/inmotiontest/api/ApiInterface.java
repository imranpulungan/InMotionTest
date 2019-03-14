package com.teknologi.labakaya.inmotiontest.api;

import com.teknologi.labakaya.inmotiontest.api.response.UserResponse;

import java.util.Map;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("/search/users?q=language:java+location:indonesia")
    Call<UserResponse> getDataUser(@Query("per_page") Integer number);
}
