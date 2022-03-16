package com.hotfoot.rapid.myapplication

import android.app.Application
import com.hotfoot.rapid.myapplication.roomDB.ProductDB


class MyApplication : Application(){
    val database: ProductDB by lazy { ProductDB.getInstance(this) }
}