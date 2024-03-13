package com.example.productapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.productapp.R
import com.example.productapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val optionArray = resources.getStringArray(R.array.option)

// Crea un adaptador para el Spinner
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionArray)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Establece el adaptador en el Spinner
        binding.spinner.adapter = adapterSpinner
    }
}