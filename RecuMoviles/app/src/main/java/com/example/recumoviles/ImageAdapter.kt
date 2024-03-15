package com.example.recumoviles

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recumoviles.modelo.ModeloImagen

class ImageAdapter(private val context: Context, private val lista: ArrayList<ModeloImagen>) :
    RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.image_item_layout, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        Glide.with(context).load(lista[position].urls.regular).into(holder.imageView)
        holder.imageView.setOnClickListener {
            val intent = Intent(context, FullImage::class.java)
            intent.putExtra("image", lista[position].urls.regular)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return lista.size
    }

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
