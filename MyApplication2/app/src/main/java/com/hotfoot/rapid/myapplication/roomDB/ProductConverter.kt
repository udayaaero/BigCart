package com.hotfoot.rapid.myapplication.roomDB

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hotfoot.rapid.myapplication.Model.Products
import java.lang.reflect.Type

class ProductConverter {
    @TypeConverter
    fun fromList(article: List<Products?>?): String? {
        if (article == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Products?>?>() {}.getType()
        return gson.toJson(article, type)
    }

    @TypeConverter
    fun toList(article: String?): List<Products>? {
        if (article == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Products?>?>() {}.getType()
        return gson.fromJson(article, type)
    }
}
