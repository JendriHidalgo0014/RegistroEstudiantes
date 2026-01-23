package edu.ucne.registro_estudiantes.data.local.dao

import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registro_estudiantes.data.local.entities.AsignaturaEntity
import kotlinx.coroutines.flow.Flow

interface AsignaturaDao {
    @Query("SELECT * FROM asignaturas ORDER BY asignaturaId DESC")
    fun observeAll(): Flow<List<AsignaturaEntity>>
    @Query("SELECT * FROM asignaturas WHERE asignaturaId = :id")
    suspend fun getById(id: Int): AsignaturaEntity?
    @Upsert
    suspend fun upsert(entity: AsignaturaEntity)

    @Delete
    suspend fun delete(entity: AsignaturaEntity)
    @Query("DELETE FROM asignaturas WHERE asignaturaId = :id")
    suspend fun deleteById(id: Int)
}