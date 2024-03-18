package com.example.productapp.data

import com.example.productapp.activity.utils.DatabaseHelper

class ProductTable(var id: Int, var title:String, var price:String) {


    companion object {

        const val TABLE_NAME = "YourTable"
        const val COLUMN_TITLE = "title"
        const val COLUMN_PRICE = "price"
        val COLUMN_NAMES= arrayOf(
            DatabaseHelper.DATABASE_NAME_ID,
            COLUMN_TITLE,
            COLUMN_PRICE)
    }

    override fun toString(): String {
        return "$id -> Task: $title - done"
    }
}