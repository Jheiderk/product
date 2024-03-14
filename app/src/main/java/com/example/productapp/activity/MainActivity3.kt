package com.example.productapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.productapp.R
import com.example.productapp.databinding.ActivityMain3Binding

import com.squareup.picasso.Picasso

class MainActivity3 : AppCompatActivity() {

    private lateinit var binding: ActivityMain3Binding

    private var title: String? = null
    private var price: String? = null
    private var rating: String? = null
    private var image: String? = null
    private var stock: String? = null
    private var description: String? = null
    private var brand: String? = null
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
        supportActionBar?.title =title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.textTitle.text=title
        binding.textPrice2.text=getString(R.string.dollar, price)
        binding.ratingText.text=rating
        Picasso.get().load(image).into(binding.image)
        binding.stockText.text=stock
        binding.descriptionText.text=description
        binding.brandText.text=brand
    }

    private fun initUI() {
        title = intent.getStringExtra("title")
        price = intent.getStringExtra("price")
        rating = intent.getStringExtra("rating")
        image = intent.getStringExtra("image")
        stock = intent.getStringExtra("stock")
        description= intent.getStringExtra("description")
        brand = intent.getStringExtra("brand")
    }
}