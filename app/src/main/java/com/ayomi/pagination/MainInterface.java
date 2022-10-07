package com.ayomi.pagination;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MainInterface {
//    @GET("v2/list")
    @GET("posts")
    Call<String> STRING_CALL(

    );
}
