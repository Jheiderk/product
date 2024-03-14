package com.example.productapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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

    private fun visualReform() {
        binding.textTitle.text=title
        binding.textPrice2.text=price
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