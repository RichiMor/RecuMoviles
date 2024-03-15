package com.example.recumoviles

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recumoviles.api.UtilidadesApi
import com.example.recumoviles.modelo.ModeloBuscador
import com.example.recumoviles.modelo.ModeloImagen
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.properties.Delegates

class Galeria : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private var list: ArrayList<ModeloImagen> = ArrayList()
    private lateinit var manager: GridLayoutManager
    private lateinit var adapter: ImageAdapter
    private var page: Int = 1
    private var pageSize: Int = 30
    private var isLoading by Delegates.notNull<Boolean>()
    private var isLastPage by Delegates.notNull<Boolean>()
    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_galeria)

        // Buscar la vista BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Configurar el listener para el BottomNavigationView
        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_home -> {
                    startActivity(Intent(this, MenuActivity::class.java))
                    true
                }
                R.id.action_gallery -> {

                    true
                }
                R.id.action_settings -> {
                    // Abrir la actividad Ajustes
                    startActivity(Intent(this, Ajustes::class.java))
                    true
                }
                else -> false
            }
        }


        isLoading = false
        isLastPage = false

        recyclerView = findViewById(R.id.recyclerView)
        adapter = ImageAdapter(this, list)
        manager = GridLayoutManager(this, 3)

        recyclerView.apply {
            layoutManager = manager
            setHasFixedSize(true)
            adapter = this@Galeria.adapter
        }
        getData()
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = manager.childCount
                val totalItemCount = manager.itemCount
                val firstVisibleItemPosition = manager.findFirstVisibleItemPosition()

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= pageSize) {
                        page++
                        getData()
                    }
                }
            }
        })
    }

    private fun getData() {
        if (isLoading) return

        isLoading = true
        UtilidadesApi.getApiInterface().getImages(page, pageSize).enqueue(object : Callback<List<ModeloImagen>> {
            override fun onResponse(call: Call<List<ModeloImagen>>, response: Response<List<ModeloImagen>>) {
                isLoading = false
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.isNotEmpty()) {
                            val startIndex = list.size
                            list.addAll(it)
                            adapter.notifyItemRangeInserted(startIndex, it.size)
                            isLastPage = it.size < pageSize
                        }
                    }
                } else {
                    Toast.makeText(this@Galeria, "Failed to load images", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<ModeloImagen>>, t: Throwable) {
                isLoading = false
                Toast.makeText(this@Galeria, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        val search = menu?.findItem(R.id.search)?.actionView as? SearchView

        search?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchData(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // Este método se llama cada vez que el texto cambia.
                // Puedes implementar la búsqueda en tiempo real aquí si es necesario
                return false
            }
        })
        return true
    }

    private fun searchData(query: String) {
        isLoading = true // Actualizar estado de carga

        UtilidadesApi.getApiInterface().searchImage(query).enqueue(object : Callback<ModeloBuscador> {
            override fun onResponse(call: Call<ModeloBuscador>, response: Response<ModeloBuscador>) {
                isLoading = false // Actualizar estado de carga
                if (response.isSuccessful) {
                    response.body()?.let {
                        list.clear()
                        list.addAll(it.resultados)
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    Toast.makeText(this@Galeria, "Failed to retrieve data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ModeloBuscador>, t: Throwable) {
                isLoading = false // Actualizar estado de carga
                Toast.makeText(this@Galeria, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}
