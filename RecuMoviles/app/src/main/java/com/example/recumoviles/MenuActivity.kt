package com.example.recumoviles
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView

class MenuActivity : AppCompatActivity() {
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Buscar la vista BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Configurar el listener para el BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    // No hagas nada ya que ya estás en la actividad Menu
                    true
                }
                R.id.action_gallery -> {
                    // Abrir la actividad Galeria
                    startActivity(Intent(this, Galeria::class.java))
                    true
                }
                R.id.action_settings -> {
                    // Abrir la actividad com.example.recumoviles.Ajustes
                    startActivity(Intent(this, Ajustes::class.java))
                    true
                }
                else -> false
            }
        }
    }
}
