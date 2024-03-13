package com.example.productapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productapp.adapter.ProductAdapter
import com.example.productapp.data.CategoryProduct
import com.example.productapp.databinding.ActivityMain2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    private lateinit var list:String
    private lateinit var binding: ActivityMain2Binding
    private lateinit var adapter: ProductAdapter

    private var title: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra("Category")
        Log.i("HTTP", "$title")

        searchCategoryProduct(title!!)
        adapter = ProductAdapter{
            adapterUI(it)
        }
        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

    }
    fun adapterUI(position:Int){


    }




    private fun searchCategoryProduct(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/products/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: CategoryProduct = retrofit.create(CategoryProduct ::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.searchByName(query)


            runOnUiThread {
                list = response.body()?.results.toString()
                adapter.updateItems(response.body()?.results)


            }
        }
    }
}