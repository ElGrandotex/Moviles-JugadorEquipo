package com.joseverdezoto.exameniib

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.joseverdezoto.exameniib.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var databaseHelper: JugadorDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = JugadorDatabaseHelper(this)

        val jugadorAdapter = JugadorAdapter(databaseHelper.getJugadores(), this)
        binding.jugadorRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.jugadorRecyclerView.adapter = jugadorAdapter

        binding.addJugadorButton.setOnClickListener {
            val intent = Intent(this, AddJugadorActivity::class.java)
            startActivity(intent)
        }

        val equipoAdapter = EquipoAdapter(databaseHelper.getEquipos(), this)
        binding.equipoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.equipoRecyclerView.adapter = equipoAdapter

        binding.addEquipoButton.setOnClickListener {
            val intent = Intent(this, AddEquipoActivity::class.java)
            startActivity(intent)
        }


    }
    override fun onResume() {
        super.onResume()
        val jugadorAdapter = JugadorAdapter(databaseHelper.getJugadores(), this)
        binding.jugadorRecyclerView.adapter = jugadorAdapter
        val equipoAdapter = EquipoAdapter(databaseHelper.getEquipos(), this)
        binding.equipoRecyclerView.adapter = equipoAdapter
    }
}