package com.hotfoot.rapid.myapplication.network

import com.hotfoot.rapid.myapplication.Model.GroceryResponse
import retrofit2.Response
import retrofit2.http.*

interface Api {

    @GET("v2/5def7b172f000063008e0aa2")
    suspend fun getApi(): Response<GroceryResponse>
}