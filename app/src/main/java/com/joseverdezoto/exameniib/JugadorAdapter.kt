package com.joseverdezoto.exameniib

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class JugadorAdapter(private var jugadores: List<Jugador>, context: Context) : RecyclerView.Adapter<JugadorAdapter.JugadorViewHolder>() {

    private val db: JugadorDatabaseHelper = JugadorDatabaseHelper(context)

    class JugadorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val nombreTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val posicionTextView: TextView = itemView.findViewById(R.id.posicionTextView)
        val dorsalTextView: TextView = itemView.findViewById(R.id.dorsalTextView)
        val equipoTextView: TextView = itemView.findViewById(R.id.equipoTextView)
        val updateJButton: ImageView = itemView.findViewById(R.id.updateJButton)
        val deleteJButton: ImageView = itemView.findViewById(R.id.deleteJButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JugadorViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.jugador_item, parent, false)
        return JugadorViewHolder(view)
    }

    override fun getItemCount(): Int = jugadores.size

    override fun onBindViewHolder(holder: JugadorViewHolder, position: Int) {
        val jugador = jugadores[position]
        holder.nombreTextView.text = jugador.nombre
        holder.posicionTextView.text = jugador.posicion
        holder.dorsalTextView.text = jugador.dorsal.toString()
        holder.equipoTextView.text = jugador.equipo.toString()

        holder.updateJButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateJugadorActivity::class.java).apply {
                putExtra("jugador_id", jugador.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteJButton.setOnClickListener {
            db.deleteJugador(jugador.id)
            refreshData(db.getJugadores())
            Toast.makeText(holder.itemView.context, "Jugador eliminado", Toast.LENGTH_SHORT).show()
        }
    }

    fun refreshData(newJugadores: List<Jugador>){
        jugadores = newJugadores
        notifyDataSetChanged()
    }

}
