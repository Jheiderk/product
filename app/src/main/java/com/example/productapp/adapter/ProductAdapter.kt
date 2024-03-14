package com.example.productapp.adapter

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.data.Product
import com.example.productapp.databinding.ItemProductBinding
import com.squareup.picasso.Picasso
import android.content.Context
import com.example.productapp.R


class ProductAdapter(private val context: Context,private var items:List<Product> = listOf(), val onClickListener: (position:Int) -> Unit, val onFavorite: (position:Int) -> Unit):
    RecyclerView.Adapter<ViewHolder>() {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("favoritos", Context.MODE_PRIVATE)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)


    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = items[position]
        holder.bind(product)
        holder.itemView.setOnClickListener {
            onClickListener(position)
        }

        // Configurar el estado de favorito según SharedPreferences
        val isFavorite = sharedPreferences.getBoolean(position.toString(), false)

        // Actualizar la imagen del icono de favorito
        holder.binding.favoriteImage.setImageResource(if (isFavorite) R.drawable.ic_favorite_on else R.drawable.ic_favorite_off)

        holder.binding.favoriteImage.setOnClickListener {
            // Cambiar el estado de favorito cuando se haga clic en el elemento
            val editor = sharedPreferences.edit()
            val newFavoriteState = !isFavorite
            editor.putBoolean(position.toString(), newFavoriteState)
            editor.apply()
            // Actualizar la UI para reflejar el cambio
            notifyItemChanged(position)
        }

    }
    fun updateItems(results: List<Product>?) = if (results != null) {
        items = results
        notifyDataSetChanged()
    }else {
        Log.i("HTTP", "es nulo")
    }
}

class ViewHolder(val binding:ItemProductBinding): RecyclerView.ViewHolder(binding.root){

    fun bind(listProduct: Product) {
        binding.textHeroe.text = listProduct.title
        Picasso.get().load(listProduct.thumbnail).into(binding.imageHero)

    }
}