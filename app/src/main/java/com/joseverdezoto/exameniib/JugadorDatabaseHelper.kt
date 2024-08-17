package com.joseverdezoto.exameniib

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class JugadorDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

    companion object{
        private const val DATABASE_NAME = "jugador_equipo.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME_JUGADOR = "jugadores"
        private const val COLUMN_ID_J = "id"
        private const val COLUMN_NOMBRE_J = "nombre"
        private const val COLUMN_POSICION_J = "posicion"
        private const val COLUMN_DORSAL_J = "dorsal"
        private const val COLUMN_EQUIPO_J = "equipo"
        private const val TABLE_NAME_EQUIPO = "equipos"
        private const val COLUMN_ID_E = "id"
        private const val COLUMN_NOMBRE_E = "nombre"
        private const val COLUMN_CIUDAD_E = "ciudad"
        private const val COLUMN_ENTRENADOR_E = "entrenador"
        private const val COLUMN_LIGA_E = "liga"
    }

    override fun onCreate(p0: SQLiteDatabase?) {
        val createTableQueryJugador = "CREATE TABLE $TABLE_NAME_JUGADOR ($COLUMN_ID_J INTEGER PRIMARY KEY, $COLUMN_NOMBRE_J TEXT, $COLUMN_POSICION_J TEXT, $COLUMN_DORSAL_J INTEGER, $COLUMN_EQUIPO_J INTEGER)"
        p0?.execSQL(createTableQueryJugador)
        val createTableQueryEquipo = "CREATE TABLE $TABLE_NAME_EQUIPO ($COLUMN_ID_E INTEGER PRIMARY KEY, $COLUMN_NOMBRE_E TEXT, $COLUMN_CIUDAD_E TEXT, $COLUMN_ENTRENADOR_E TEXT, $COLUMN_LIGA_E TEXT)"
        p0?.execSQL(createTableQueryEquipo)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        val dropTableQueryJugador = "DROP TABLE IF EXISTS $TABLE_NAME_JUGADOR"
        p0?.execSQL(dropTableQueryJugador)
        val dropTableQueryEquipo = "DROP TABLE IF EXISTS $TABLE_NAME_EQUIPO"
        p0?.execSQL(dropTableQueryEquipo)
        onCreate(p0)
    }

    fun insertJugador(jugador: Jugador){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_J, jugador.nombre)
            put(COLUMN_POSICION_J, jugador.posicion)
            put(COLUMN_DORSAL_J, jugador.dorsal)
            put(COLUMN_EQUIPO_J, jugador.equipo)
        }
        db.insert(TABLE_NAME_JUGADOR, null, values)
        db.close()
    }

    fun insertEquipo(equipo: Equipo){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_E, equipo.nombre)
            put(COLUMN_CIUDAD_E, equipo.ciudad)
            put(COLUMN_ENTRENADOR_E, equipo.entrenador)
            put(COLUMN_LIGA_E, equipo.liga)
        }
        db.insert(TABLE_NAME_EQUIPO, null, values)
        db.close()
    }

    fun getJugadores(): List<Jugador>{
        val jugadorList = mutableListOf<Jugador>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_JUGADOR"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_J))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_J))
            val posicion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSICION_J))
            val dorsal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DORSAL_J))
            val equipo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EQUIPO_J))

            val jugador = Jugador(id, nombre, posicion, dorsal, equipo)
            jugadorList.add(jugador)
        }
        cursor.close()
        db.close()
        return jugadorList
    }

    fun getEquipos(): List<Equipo>{
        val equipoList = mutableListOf<Equipo>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_EQUIPO"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_E))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_E))
            val ciudad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CIUDAD_E))
            val entrenador = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRENADOR_E))
            val liga = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIGA_E))

            val equipo = Equipo(id, nombre, ciudad, entrenador, liga)
            equipoList.add(equipo)
        }
        cursor.close()
        db.close()
        return equipoList
    }

    fun getJugadoresByEquipos(equipoId: Int): List<Jugador>{
        val jugadorList = mutableListOf<Jugador>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_JUGADOR WHERE $COLUMN_EQUIPO_J = $equipoId"
        val cursor = db.rawQuery(query, null)

        while (cursor.moveToNext()){
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_J))
            val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_J))
            val posicion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSICION_J))
            val dorsal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DORSAL_J))

            val jugador = Jugador(id, nombre, posicion, dorsal, equipoId)
            jugadorList.add(jugador)
        }
        cursor.close()
        db.close()
        return jugadorList
    }

    fun updateJugador(jugador: Jugador){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_J, jugador.nombre)
            put(COLUMN_POSICION_J, jugador.posicion)
            put(COLUMN_DORSAL_J, jugador.dorsal)
            put(COLUMN_EQUIPO_J, jugador.equipo)
        }
        val whereClause = "$COLUMN_ID_J = ?"
        val whereArgs = arrayOf(jugador.id.toString())
        db.update(TABLE_NAME_JUGADOR, values, whereClause, whereArgs)
        db.close()
    }

    fun getJugadorById(jugadorId: Int): Jugador? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_JUGADOR WHERE $COLUMN_ID_J = $jugadorId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val ide = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_J))
        val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_J))
        val posicion = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_POSICION_J))
        val dorsal = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DORSAL_J))
        val equipo = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_EQUIPO_J))

        cursor.close()
        db.close()
        return Jugador(ide, nombre, posicion, dorsal, equipo)
    }

    fun updateEquipo(equipo: Equipo){
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NOMBRE_E, equipo.nombre)
            put(COLUMN_LIGA_E, equipo.liga)
            put(COLUMN_CIUDAD_E, equipo.ciudad)
            put(COLUMN_ENTRENADOR_E, equipo.entrenador)
        }
        val whereClause = "$COLUMN_ID_E = ?"
        val whereArgs = arrayOf(equipo.id.toString())
        db.update(TABLE_NAME_EQUIPO, values, whereClause, whereArgs)
        db.close()
    }

    fun getEquipoById(equipoId: Int): Equipo? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME_EQUIPO WHERE $COLUMN_ID_E = $equipoId"
        val cursor = db.rawQuery(query, null)
        cursor.moveToFirst()

        val ide = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID_E))
        val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE_E))
        val ciudad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CIUDAD_E))
        val entrenador = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ENTRENADOR_E))
        val liga = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_LIGA_E))

        cursor.close()
        db.close()
        return Equipo(ide, nombre, ciudad, entrenador, liga)
    }

    fun deleteJugador(jugadorId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID_J = ?"
        val whereArgs = arrayOf(jugadorId.toString())
        db.delete(TABLE_NAME_JUGADOR, whereClause, whereArgs)
        db.close()
    }

    fun deleteEquipo(equipoId: Int){
        val db = writableDatabase
        val whereClause = "$COLUMN_ID_E = ?"
        val whereArgs = arrayOf(equipoId.toString())
        db.delete(TABLE_NAME_EQUIPO, whereClause, whereArgs)
        db.close()
    }
}