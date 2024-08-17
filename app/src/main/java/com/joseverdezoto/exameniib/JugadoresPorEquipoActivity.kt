package com.joseverdezoto.exameniib

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager

import com.joseverdezoto.exameniib.R
import com.joseverdezoto.exameniib.databinding.ActivityJugadoresPorEquipoBinding

class JugadoresPorEquipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJugadoresPorEquipoBinding
    private lateinit var databaseHelper: JugadorDatabaseHelper
    private var equipoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJugadoresPorEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = JugadorDatabaseHelper(this)

        equipoId = intent.getIntExtra("equipo_id", -1)
        if (equipoId == -1) {
            finish()
            return
        }

        val jugadores = JugadorAdapter(databaseHelper.getJugadoresByEquipos(equipoId), this)
    binding.jugadorPorEquipoRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.jugadorPorEquipoRecyclerView.adapter = jugadores




    }
}