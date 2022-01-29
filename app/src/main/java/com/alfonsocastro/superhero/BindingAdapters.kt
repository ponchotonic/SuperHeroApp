package com.alfonsocastro.superhero

import android.util.Log
import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.alfonsocastro.superhero.adapters.HeroAdapter
import com.alfonsocastro.superhero.model.Hero

@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    Log.d("BindingAdapters", "Loading $imgUrl into ImageView...")
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
        imgView.load(imgUri) {
            placeholder(R.drawable.ic_broken_image)
            error(R.drawable.ic_broken_image)
        }
    }
}

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     data: List<Hero>?) {
    val adapter = recyclerView.adapter as HeroAdapter
    Log.d("BindingAdapters", "Adapter has items? ${adapter.itemCount} items")
    adapter.submitList(data)
    Log.d("BindingAdapters", "Adapter has items? ${adapter.itemCount} items")
}