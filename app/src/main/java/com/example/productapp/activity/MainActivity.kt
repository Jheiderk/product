package com.example.productapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.productapp.R
import com.example.productapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var category: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
        selected()

        binding.next.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)

            intent.putExtra("Category", category)

            startActivity(intent)
        }


    }

    private fun selected() {
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                // Recupera la opción seleccionada
                category= parent.getItemAtPosition(position).toString()

            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Maneja el caso en que no se seleccione nada
            }
        }
    }

    private fun initUI() {
        val optionArray = resources.getStringArray(R.array.option)

// Crea un adaptador para el Spinner
        val adapterSpinner = ArrayAdapter(this, android.R.layout.simple_spinner_item, optionArray)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

// Establece el adaptador en el Spinner
        binding.spinner.adapter = adapterSpinner


    }
}