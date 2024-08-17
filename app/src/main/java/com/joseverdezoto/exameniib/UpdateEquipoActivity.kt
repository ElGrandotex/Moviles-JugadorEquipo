package com.joseverdezoto.exameniib

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.joseverdezoto.exameniib.databinding.ActivityUpdateEquipoBinding

class UpdateEquipoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateEquipoBinding
    private lateinit var databaseHelper: JugadorDatabaseHelper
    private var equipoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateEquipoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = JugadorDatabaseHelper(this)

        equipoId = intent.getIntExtra("equipo_id", -1)
        if (equipoId == -1) {
            finish()
            return
        }

        val equipo = databaseHelper.getEquipoById(equipoId)
        binding.updateNameEEditText.setText(equipo?.nombre)
        binding.updateCiudadEditText.setText(equipo?.ciudad)
        binding.updateEntrenadorEditText.setText(equipo?.entrenador)
        binding.updateLigaEditText.setText(equipo?.liga)

        binding.updateEquipoSaveButton.setOnClickListener {
            val newName = binding.updateNameEEditText.text.toString()
            val newCiudad = binding.updateCiudadEditText.text.toString()
            val newEntrenador = binding.updateEntrenadorEditText.text.toString()
            val newLiga = binding.updateLigaEditText.text.toString()

            val updatedEquipo = Equipo(equipoId, newName, newCiudad, newEntrenador, newLiga)
            databaseHelper.updateEquipo(updatedEquipo)
            finish()
            Toast.makeText(this, "Equipo actualizado", Toast.LENGTH_SHORT).show()
        }


    }
}