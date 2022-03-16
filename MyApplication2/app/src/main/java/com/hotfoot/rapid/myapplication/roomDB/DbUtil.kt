package com.hotfoot.rapid.myapplication.roomDB

import android.app.Activity
import android.content.Context
import com.hotfoot.rapid.myapplication.Model.Products
import com.hotfoot.rapid.myapplication.MyApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

object DbUtil {

    fun addProductList(context: Activity, productList:Products) {
        CoroutineScope(Dispatchers.IO).launch {
            (context.application as MyApplication).database.productDao()
                .also {
                    productList.let {
                        (context.application as MyApplication).database.productDao()
                            .addProduct(it)
                    }
                }
        }
    }

    fun getAllProduct(activity: Activity):List<Products>? {
        var list:List<Products>?=null
        CoroutineScope(Dispatchers.IO).launch {
             list = ProductDB.getInstance(activity).productDao().getAllProduct()

        }
        return  list}
}

