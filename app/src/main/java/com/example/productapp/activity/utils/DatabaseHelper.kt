package com.example.productapp.activity.utils

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.productapp.data.ProductTable

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "to_do_app.db"
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME_ID = "id"

        private const val SQL_CREATE_TABLE  =
            "CREATE TABLE ${ProductTable.TABLE_NAME} (" +
                "$DATABASE_NAME_ID TEXT PRIMARY KEY, " +
                "${ProductTable.COLUMN_TITLE} TEXT, " +
                "${ProductTable.COLUMN_PRICE} TEXT)"

        private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS Task"
    }


    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        //if (oldVersion== 1){
        db.execSQL(SQL_DELETE_TABLE)

      //  }
        destroyDatabase(db)
        onCreate(db)
    }

    private fun destroyDatabase (db: SQLiteDatabase) {
        db.execSQL(SQL_DELETE_TABLE)
    }



}