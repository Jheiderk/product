package com.example.productapp.data

import com.google.gson.annotations.SerializedName

data class ProductRespond(
    @SerializedName("products") val results:List<Product>
) {

}

class Product (

    @SerializedName("id") val id:String,
    @SerializedName("title") val title:String,
    @SerializedName("description") val description:String,
    @SerializedName("thumbnail") val thumbnail:String,
    @SerializedName("price") val price:String

)
