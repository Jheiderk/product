package com.example.productapp.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CategoryProduct {

    @GET("category/{category}")
    suspend fun searchByName(@Path("category") query:String) : Response<ProductRespond>


}
