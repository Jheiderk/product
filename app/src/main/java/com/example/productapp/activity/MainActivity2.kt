package com.example.productapp.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import com.example.productapp.R
import com.example.productapp.adapter.ProductAdapter
import com.example.productapp.data.CategoryProduct
import com.example.productapp.data.Product
import com.example.productapp.data.ProductTable
import com.example.productapp.data.provider.ProductDAO
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
    private lateinit var selection: ProductTable
    private val taskDAO= ProductDAO(this)


    private var title: String? =null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        title = intent.getStringExtra("Category")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title =title

        searchCategoryProduct(title!!)

        adapter = ProductAdapter(list
        ) { position ->
            adapterUI(position)
        }

        binding.recyclerView.adapter = adapter

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

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

        productUI(productList)

        val intent = Intent(this, MainActivity3::class.java)

        intent.putExtra("id", productList.id)
        startActivity(intent)

    }


    private fun searchCategoryProduct(query: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/products/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: CategoryProduct = retrofit.create(CategoryProduct::class.java)

        CoroutineScope(Dispatchers.IO).launch {

            try {
                val response = service.searchByName(query)
                runOnUiThread {
                    if (response.isSuccessful) {
                        list = response.body()?.results!!
                        adapter.updateItems(response.body()?.results)

                    } else {
                        // La respuesta no fue exitosa, manejar el caso en consecuencia
                        showAlertDialog(getString(R.string.failed))

                    }
                }
            } catch (e: Exception) {
                // Ocurrió una excepción, manejarla de acuerdo a tus necesidades
                showAlertDialog(getString(R.string.failed))

            }

        }

    }
    private fun listProduct() {

        val existingProduct = taskDAO.find(selection.id.toString())
        if (existingProduct != null) {
            Log.i("DATABASE", "El producto con ID ${existingProduct.id} ya existe en la tabla.")
            // Aquí decides qué hacer con el producto existente
            // Puedes actualizarlo, notificar al usuario, etc.
        } else {
            taskDAO.insert(selection)
            Log.i("DATABASE", "Nuevo producto insertado: $selection")
        }

    }

    private fun productUI(list: Product) {


        selection = ProductTable(list.id.toInt(),list.title,list.price,list.rating,list.thumbnail,list.stock,list.description,list.brand )
        listProduct()

    }

    private fun showAlertDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message)
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
                finish() // Finalizar la actividad actual y volver a la actividad anterior
            }
            .setCancelable(false)
            .show()
    }
}