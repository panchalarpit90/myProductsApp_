package com.example.marsrealestate.network


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

enum class MarsApiFilter(val value:String){SHOW_ALL("all"),RENT("rent"),BUY("buy")}
private const val BASE_URL = "https://mars.udacity.com/"

interface MarsApiService {
    @GET("realestate")
    suspend fun getProperties(@Query("filter")type:String): List<MarsProperty>
}


object MarsApi {
    val retrofitService : MarsApiService
        init {
            val retrofit=Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create())
                .build()
            retrofitService=retrofit.create(MarsApiService::class.java)

        }
}