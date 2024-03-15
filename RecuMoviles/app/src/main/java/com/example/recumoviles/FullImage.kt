package com.example.recumoviles

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide

class FullImage : AppCompatActivity() {
    private lateinit var imageView:ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)

        imageView = findViewById(R.id.myZoomageView)

        Glide.with(this).load(intent.getStringExtra("image")).into(imageView)

    }
}