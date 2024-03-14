package com.example.productapp.adapter

import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.recyclerview.widget.RecyclerView
import com.example.productapp.data.Product
import com.example.productapp.databinding.ItemProductBinding
import com.squareup.picasso.Picasso



class ProductAdapter(private var items:List<Product>, val onClickListener: (position:Int) -> Unit):
    RecyclerView.Adapter<ViewHolder>() {

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