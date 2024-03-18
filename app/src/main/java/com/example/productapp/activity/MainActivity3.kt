package com.example.productapp.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.example.productapp.R

import com.example.productapp.data.ProductTable
import com.example.productapp.data.provider.ProductDAO
import com.example.productapp.databinding.ActivityMain3Binding
import com.squareup.picasso.Picasso



class MainActivity3 : AppCompatActivity() {


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var binding: ActivityMain3Binding
    private val productDAO = ProductDAO(this)
    private lateinit var specificData: ProductTable

    private var id: String?=null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain3Binding.inflate(layoutInflater)
        setContentView(binding.root)


        initUI()
        visualReform()



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


        val isFavorite = sharedPreferences.getBoolean("favorito_${specificData.title}", false)
        binding.favoriteImage.setImageResource(if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)

        // Manejar el clic en el icono de favorito
        binding.favoriteImage.setOnClickListener {
            toggleFavoriteState()
        }


    }

    private fun toggleFavoriteState() {
        // Obtener el estado actual de favorito
        val isFavorite = sharedPreferences.getBoolean("favorito_${specificData.title}", false)

        // Cambiar el estado de favorito
        val editor = sharedPreferences.edit()
        editor.putBoolean("favorito_${specificData.title}", !isFavorite)
        editor.apply()

        // Actualizar la imagen del icono de favorito
        binding.favoriteImage.setImageResource(if (!isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)
    }

    private fun initUI() {

        id= intent.getStringExtra("id")

        specificData = productDAO.find(id!!)!!

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
            binding.ratingText.text=productData.rating
            Picasso.get().load(productData.thumbnail).into(binding.image)
            binding.stockText.text=productData.stock
            binding.descriptionText.text=productData.description
            binding.brandText.text=productData.brand

            // Otros campos de datos
        } else {

            // Manejar el caso en el que no se obtengan datos de la base de datos
        }
    }



}