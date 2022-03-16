package com.hotfoot.rapid.myapplication.Model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable
@Entity(tableName = "ProductTable")
data class Products (
    @PrimaryKey(autoGenerate = true) var ids: Int = 0,
    @SerializedName("name"        ) var name        : String?           = null,
    @SerializedName("id"          ) var id          : String?           = null,
    @SerializedName("product_id"  ) var productId   : String?           = null,
    @SerializedName("sku"         ) var sku         : String?           = null,
    @SerializedName("image"       ) var image       : String?           = null,
    @SerializedName("thumb"       ) var thumb       : String?           = null,
    @SerializedName("zoom_thumb"  ) var zoomThumb   : String?           = null,
    @SerializedName("options"     ) var options     : ArrayList<String> = arrayListOf(),
    @SerializedName("description" ) var description : String?           = null,
    @SerializedName("href"        ) var href        : String?           = null,
    @SerializedName("quantity"    ) var quantity    : Int?              = null,
    @SerializedName("images"      ) var images      : ArrayList<String> = arrayListOf(),
    @SerializedName("price"       ) var price       : String?           = null,
    @SerializedName("special"     ) var special     : String?           = null,
    @SerializedName("subTotal"    ) var subTotal    : Int?              = null,
    @SerializedName("total"    ) var total    : Int?              = null

):Serializable
