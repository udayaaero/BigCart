package com.hotfoot.rapid.myapplication.network


class ApiHelper {
    suspend fun getGrocery() = RetrofitBuilder.GroceryApi.getApi()
}