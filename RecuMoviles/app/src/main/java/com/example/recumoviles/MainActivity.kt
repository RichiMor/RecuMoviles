package com.example.recumoviles

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    private lateinit var btnNav:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnNav = findViewById(R.id.btnIr)
        btnNav.setOnClickListener {
            val intent = Intent(this, Galeria::class.java)
            startActivity(intent)
        }

    }
}