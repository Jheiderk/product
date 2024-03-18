package com.example.productapp.data.provider

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import com.example.productapp.activity.utils.DatabaseHelper
import com.example.productapp.data.ProductTable

class ProductDAO(context: Context) {

    private val databaseManager: DatabaseHelper = DatabaseHelper(context)


    fun insert(task: ProductTable): ProductTable {
        val db = databaseManager.writableDatabase
        try {
            val values = ContentValues().apply {
                put(DatabaseHelper.DATABASE_NAME_ID, task.id)
                put(ProductTable.COLUMN_TITLE, task.title)
                put(ProductTable.COLUMN_PRICE, task.price)
                put(ProductTable.COLUMN_RATING, task.rating)
                put(ProductTable.COLUMN_THUMBNAIL, task.thumbnail)
                put(ProductTable.COLUMN_STOCK, task.stock)
                put(ProductTable.COLUMN_DESCRIPTION, task.description)
                put(ProductTable.COLUMN_BRAND, task.brand)
            }
            val newRowId = db.insertOrThrow(ProductTable.TABLE_NAME, null, values)
            Log.i("DATABASE", "New record id: $newRowId")
            db.close()
            task.id = newRowId.toInt()
            return task
        } catch (e: SQLiteConstraintException) {
            Log.e("DATABASE", "Error de clave única: ${e.message}")
            // Manejar la excepción aquí (actualización, notificación, etc.)
            // Puedes lanzar una excepción personalizada o manejarla de otra manera.
            throw e
        }

    }

    fun update(task: ProductTable) {

        val db = databaseManager.writableDatabase


        var values = ContentValues().apply {
            put(ProductTable.COLUMN_TITLE, task.title)
            put(ProductTable.COLUMN_PRICE, task.price)
            put(ProductTable.COLUMN_RATING, task.rating)
            put(ProductTable.COLUMN_THUMBNAIL, task.thumbnail)
            put(ProductTable.COLUMN_STOCK, task.stock)
            put(ProductTable.COLUMN_DESCRIPTION, task.description)
            put(ProductTable.COLUMN_BRAND, task.brand)

        }

        var updatedRows = db.update(ProductTable.TABLE_NAME, values, "${DatabaseHelper.DATABASE_NAME_ID}=?", arrayOf(task.id.toString()))
        Log.i("DATABASE", "Updated records: $updatedRows"
        )
        db.close()

    }

    fun delete(task: ProductTable) {

        val db = databaseManager.writableDatabase


        val deletedRows = db.delete(ProductTable.TABLE_NAME, "${DatabaseHelper.DATABASE_NAME_ID}=?", arrayOf(task.id.toString()))
        Log.i("DATABASE", "Deleted rows: $deletedRows")

        db.close()
    }

    @SuppressLint("Range")
    fun find(Id:String): ProductTable?{
        val db = databaseManager.readableDatabase
        val cursor = db.query(
            ProductTable.TABLE_NAME,
            ProductTable.COLUMN_NAMES,
            "${DatabaseHelper.DATABASE_NAME_ID} = ?",
            arrayOf(Id),
            null,
            null,
            null
        )

        var task: ProductTable? = null

        if (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DATABASE_NAME_ID))
            val taskName = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_TITLE))
            val taskPrice = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_PRICE))
            val taskRating = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_RATING))
            val taskThumbnail = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_THUMBNAIL))
            val taskStock = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_STOCK))
            val taskDescription = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_DESCRIPTION))
            val taskBrand = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_BRAND))



            task = ProductTable(id, taskName, taskPrice, taskRating, taskThumbnail, taskStock, taskDescription, taskBrand)
        }
        cursor.close()
        db.close()
        return task



    }

    @SuppressLint("Range")
    fun findAll(): MutableList<ProductTable> {

        val db = databaseManager.readableDatabase
        val cursor = db.query(
            ProductTable.TABLE_NAME,                 // The table to query
            ProductTable.COLUMN_NAMES,     // The array of columns to return (pass null to get all)
            null,                // The columns for the WHERE clause
            null,          // The values for the WHERE clause
            null,                   // don't group the rows
            null,                   // don't filter by row groups
            null              // The sort order
        )

        var list: MutableList<ProductTable> = mutableListOf()

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.DATABASE_NAME_ID))
            val taskName = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_TITLE))
            val taskPrice = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_PRICE))
            val taskRating = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_RATING))
            val taskThumbnail = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_THUMBNAIL))
            val taskStock = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_STOCK))
            val taskDescription = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_DESCRIPTION))
            val taskBrand = cursor.getString(cursor.getColumnIndex(ProductTable.COLUMN_BRAND))


            val task: ProductTable = ProductTable(id, taskName, taskPrice, taskRating, taskThumbnail, taskStock, taskDescription, taskBrand)

            list.add(task)
        }
        cursor.close()
        db.close()

        return list

    }

}