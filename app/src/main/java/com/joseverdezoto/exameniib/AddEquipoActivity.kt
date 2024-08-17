package com.joseverdezoto.exameniib

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.joseverdezoto.exameniib.databinding.ActivityAddEquipoBinding

class AddEquipoActivity: AppCompatActivity() {
    private lateinit var binding: ActivityAddEquipoBinding
    private lateinit var databaseHelper: JugadorDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = JugadorDatabaseHelper(this)

        binding.saveButton.setOnClickListener {
            val nombre = binding.nameEditText.text.toString()
            val ciudad = binding.ciudadEditText.text.toString()
            val entrenador = binding.entrenadorEditText.text.toString()
            val liga = binding.ligaEditText.text.toString()
            val equipo = Equipo(0, nombre, ciudad, entrenador, liga)
            databaseHelper.insertEquipo(equipo)
            finish()
            Toast.makeText(this, "Equipo agregado", Toast.LENGTH_SHORT).show()
        }
    }
}