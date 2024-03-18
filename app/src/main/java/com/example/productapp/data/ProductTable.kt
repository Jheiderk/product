package com.example.productapp.data

import com.example.productapp.activity.utils.DatabaseHelper

class ProductTable(var id: Int, var title:String, var price:String, var rating:String, var thumbnail:String,var stock:String, var description:String, var brand:String) {


    companion object {

        const val TABLE_NAME = "YourTable"
        const val COLUMN_TITLE = "title"
        const val COLUMN_PRICE = "price"
        const val COLUMN_RATING = "rating"
        const val COLUMN_THUMBNAIL = "thumbnail"
        const val COLUMN_STOCK = "stock"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_BRAND = "brand"


        val COLUMN_NAMES= arrayOf(
            DatabaseHelper.DATABASE_NAME_ID,
            COLUMN_TITLE,
            COLUMN_PRICE,
            COLUMN_RATING,
            COLUMN_THUMBNAIL,
            COLUMN_STOCK,
            COLUMN_DESCRIPTION,
            COLUMN_BRAND)
    }

    override fun toString(): String {
        return "$id -> Task: $title - done"
    }
}