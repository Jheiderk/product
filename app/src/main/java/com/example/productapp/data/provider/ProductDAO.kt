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


        var values = ContentValues()
        values.put(ProductTable.COLUMN_TITLE, task.title)
        values.put(ProductTable.COLUMN_PRICE, task.price)

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
            task = ProductTable(id, taskName, taskPrice)
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


            val task: ProductTable = ProductTable(id, taskName, taskPrice)

            list.add(task)
        }
        cursor.close()
        db.close()

        return list

    }

}