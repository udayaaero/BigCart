package com.hotfoot.rapid.myapplication.Model

import com.google.gson.annotations.SerializedName

data class GroceryResponse(
    @SerializedName("products" ) var products : ArrayList<Products> = arrayListOf()
)
