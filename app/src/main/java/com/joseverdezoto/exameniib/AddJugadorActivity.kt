package com.joseverdezoto.exameniib

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.joseverdezoto.exameniib.databinding.ActivityAddJugadorBinding

class AddJugadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddJugadorBinding
    private lateinit var databaseHelper: JugadorDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddJugadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = JugadorDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val nombre = binding.nameEditText.text.toString()
            val posicion = binding.posicionEditText.text.toString()
            val dorsal = binding.dorsalEditText.text.toString().toInt()
            val equipo = binding.equipoEditText.text.toString().toInt()
            val jugador = Jugador(0, nombre, posicion, dorsal, equipo)
            databaseHelper.insertJugador(jugador)
            finish()
            Toast.makeText(this, "Jugador agregado", Toast.LENGTH_SHORT).show()
        }
    }
}