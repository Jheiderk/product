package com.example.productapp.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.productapp.R
import com.example.productapp.activity.utils.DatabaseHelper

import com.example.productapp.data.CategoryProduct
import com.example.productapp.data.Product
import com.example.productapp.data.ProductTable
import com.example.productapp.data.provider.ProductDAO
import com.example.productapp.databinding.ActivityMain3Binding

import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity3 : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMain3Binding
    private lateinit var list:Product
    private val productDAO = ProductDAO(this)
    private lateinit var selection: MutableList<ProductTable>
    private var id: String?=null
    private var title: String?=null
    private lateinit var dbHelper: DatabaseHelper



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)



        initUI()

        Log.i("http", id.toString())
        searchCategoryProduct(id!!)



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

    private fun visualReform() {



        //binding.textTitle.text= list.title
        //binding.textPrice2.text=getString(R.string.dollar, list.price)
        binding.ratingText.text=list.rating
        Picasso.get().load(list.thumbnail).into(binding.image)
        binding.stockText.text=list.stock
        binding.descriptionText.text=list.description
        binding.brandText.text=list.brand

        val isFavorite = sharedPreferences.getBoolean("favorito_${title}", false)
        binding.favoriteImage.setImageResource(if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)

        // Manejar el clic en el icono de favorito
        binding.favoriteImage.setOnClickListener {
            toggleFavoriteState()
        }


    }

    private fun toggleFavoriteState() {
        // Obtener el estado actual de favorito
        val isFavorite = sharedPreferences.getBoolean("favorito_${title}", false)

        // Cambiar el estado de favorito
        val editor = sharedPreferences.edit()
        editor.putBoolean("favorito_${title}", !isFavorite)
        editor.apply()

        // Actualizar la imagen del icono de favorito
        binding.favoriteImage.setImageResource(if (!isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
    }

    private fun initUI() {

        title= intent.getStringExtra("title")
        id= intent.getStringExtra("id")

        val specificData = productDAO.find(id!!)




        displayProductData(specificData)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        sharedPreferences = getSharedPreferences("favoritos", Context.MODE_PRIVATE)
        supportActionBar?.title =title


    }
    private fun displayProductData(productData: ProductTable?) {
        if (productData != null) {
            // Mostrar los datos del producto en la interfaz de usuario
            binding.textTitle.text = productData.title
            binding.textPrice2.text = getString(R.string.dollar, productData.price)
            Log.i("DATABASE", productData.title)
            // Otros campos de datos
        } else {

            // Manejar el caso en el que no se obtengan datos de la base de datos
        }
    }

    private fun searchCategoryProduct(id: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/products/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: CategoryProduct = retrofit.create(CategoryProduct ::class.java)


        CoroutineScope(Dispatchers.IO).launch {
            val response = service.searchById(id)


            runOnUiThread {
                val responseBody = response.body()
                if (responseBody != null) {
                    list = responseBody
                    visualReform()
                } else {
                    Log.i("http","null")
                }

            }
        }
    }

}