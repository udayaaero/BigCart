package com.hotfoot.rapid.myapplication.roomDB

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {
    private val gson = Gson()
    @TypeConverter
    fun stringToList(data: String?): ArrayList<String> {
        if (data == null) {
            return ArrayList()
        }
        val listType = object : TypeToken<ArrayList<String>>() {

        }.type

        return gson.fromJson<ArrayList<String>>(data, listType)
    }

    @TypeConverter

    fun listToString(objects: ArrayList<String>): String {
        return gson.toJson(objects)
    }
}