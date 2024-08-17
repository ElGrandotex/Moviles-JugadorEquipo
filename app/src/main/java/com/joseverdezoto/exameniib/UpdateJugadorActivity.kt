package com.joseverdezoto.exameniib

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.joseverdezoto.exameniib.databinding.ActivityUpdateJugadorBinding

class UpdateJugadorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateJugadorBinding
    private lateinit var databaseHelper: JugadorDatabaseHelper
    private var jugadorId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateJugadorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = JugadorDatabaseHelper(this)

        jugadorId = intent.getIntExtra("jugador_id", -1)
        if (jugadorId == -1) {
            finish()
            return
        }

        val jugador = databaseHelper.getJugadorById(jugadorId)
        binding.updateNameEditText.setText(jugador?.nombre)
        binding.updatePosicionEditText.setText(jugador?.posicion)
        binding.updateDorsalEditText.setText(jugador?.dorsal.toString())
        binding.updateEquipoEditText.setText(jugador?.equipo.toString())

        binding.updateSaveButton.setOnClickListener {
            val newName = binding.updateNameEditText.text.toString()
            val newPosicion = binding.updatePosicionEditText.text.toString()
            val newDorsal = binding.updateDorsalEditText.text.toString().toInt()
            val newEquipo = binding.updateEquipoEditText.text.toString().toInt()

            val updatedJugador = Jugador(jugadorId, newName, newPosicion, newDorsal, newEquipo)
            databaseHelper.updateJugador(updatedJugador)
            finish()
            Toast.makeText(this, "Jugador actualizado", Toast.LENGTH_SHORT).show()
        }
    }
}