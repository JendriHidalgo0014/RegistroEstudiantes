package edu.ucne.registro_estudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registro_estudiantes.data.local.dao.EstudianteDao
import edu.ucne.registro_estudiantes.data.local.entities.EstudianteEntity

@Database(
    entities = [
        EstudianteEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class EstudianteDatabase : RoomDatabase(){

    abstract fun estudianteDao(): EstudianteDao
}