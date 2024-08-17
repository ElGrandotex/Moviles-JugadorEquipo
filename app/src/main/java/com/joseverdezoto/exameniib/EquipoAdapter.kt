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

class EquipoAdapter(private var equipos: List<Equipo>, context: Context) : RecyclerView.Adapter<EquipoAdapter.EquipoViewHolder>(){

    private val db: JugadorDatabaseHelper = JugadorDatabaseHelper(context)

    class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val idTextView: TextView = itemView.findViewById(R.id.idETextView)
        val nombreTextView: TextView = itemView.findViewById(R.id.nombreETextView)
        val ciudadTextView: TextView = itemView.findViewById(R.id.ciudadTextView)
        val entrenadorTextView: TextView = itemView.findViewById(R.id.entrenadorTextView)
        val ligaTextView: TextView = itemView.findViewById(R.id.ligaTextView)
        val updateEButton: ImageView = itemView.findViewById(R.id.updateEButton)
        val deleteEButton: ImageView = itemView.findViewById(R.id.deleteEButton)
        val showJugadores: ImageView = itemView.findViewById(R.id.showJugadores)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.equipo_item, parent, false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = equipos[position]
        holder.idTextView.text = equipo.id.toString()
        holder.nombreTextView.text = equipo.nombre
        holder.ciudadTextView.text = equipo.ciudad
        holder.entrenadorTextView.text = equipo.entrenador
        holder.ligaTextView.text = equipo.liga

        holder.updateEButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateEquipoActivity::class.java).apply {
                putExtra("equipo_id", equipo.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteEButton.setOnClickListener {
            db.deleteEquipo(equipo.id)
            refreshData(db.getEquipos())
            Toast.makeText(holder.itemView.context, "Equipo eliminado", Toast.LENGTH_SHORT).show()
        }

        holder.showJugadores.setOnClickListener {
            val intent = Intent(holder.itemView.context, JugadoresPorEquipoActivity::class.java).apply {
                putExtra("equipo_id", equipo.id)
            }
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = equipos.size

    fun refreshData(newEquipos: List<Equipo>){
        equipos = newEquipos
        notifyDataSetChanged()
    }

}