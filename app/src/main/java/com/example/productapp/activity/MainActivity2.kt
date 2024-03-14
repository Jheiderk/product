package com.example.productapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productapp.adapter.ProductAdapter
import com.example.productapp.data.CategoryProduct
import com.example.productapp.data.Product
import com.example.productapp.databinding.ActivityMain2Binding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity2 : AppCompatActivity() {
    private var list:List<Product> = listOf()
    private lateinit var binding: ActivityMain2Binding
    private lateinit var adapter: ProductAdapter

    private var title: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra("Category")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =title

        searchCategoryProduct(title!!)



        adapter = ProductAdapter(this, list, { position ->
            adapterUI(position)
        }, { position -> onFavorite(position) }
        )

        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

    }

    private fun onFavorite(position: Int) {
        TODO("Not yet implemented")
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                // Manejar el clic del botón de retroceso
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    fun adapterUI(position:Int){
        val productList =list[position]

        val intent = Intent(this, MainActivity3::class.java)
        intent.putExtra("rating", productList.rating)
        intent.putExtra("image", productList.thumbnail)
        intent.putExtra("stock", productList.stock)
        intent.putExtra("title", productList.title)
        intent.putExtra("price", productList.price)
        intent.putExtra("description", productList.description)
        intent.putExtra("brand", productList.brand)
        startActivity(intent)

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
                list = response.body()?.results!!
                adapter.updateItems(response.body()?.results)


            }
        }
    }
}