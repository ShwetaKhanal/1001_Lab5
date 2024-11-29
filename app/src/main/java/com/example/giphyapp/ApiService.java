package com.example.giphyapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {
    @GET("v1/gifs/search")
    Call<GiphyResponse> searchGIFs(
            @Query("api_key") String apiKey,
            @Query("q") String query,
            @Query("limit") int limit
    );
}

