package edu.ucne.registro_estudiantes.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.registro_estudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registro_estudiantes.data.local.dao.EstudianteDao
import edu.ucne.registro_estudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registro_estudiantes.data.local.entities.AsignaturaEntity
import edu.ucne.registro_estudiantes.data.local.entities.EstudianteEntity
import edu.ucne.registro_estudiantes.data.local.entities.TipoPenalidadEntity

@Database(
    entities = [
        EstudianteEntity::class,
        AsignaturaEntity::class,
        TipoPenalidadEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class EstudianteDatabase : RoomDatabase(){
    abstract fun estudianteDao(): EstudianteDao
    abstract fun asignaturaDao(): AsignaturaDao
    abstract fun tipoPenalidadDao(): TipoPenalidadDao

}