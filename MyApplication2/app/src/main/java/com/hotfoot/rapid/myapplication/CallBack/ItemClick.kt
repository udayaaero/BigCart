package com.hotfoot.rapid.myapplication.CallBack

import com.hotfoot.rapid.myapplication.Model.Products
import java.text.FieldPosition

interface ItemClick {
    fun ItemClick(products: Products,position: Int)
}