package edu.ucne.registro_estudiantes.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import edu.ucne.registro_estudiantes.data.local.entities.TipoPenalidadEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TipoPenalidadDao {
    @Query("SELECT * FROM tipospenalidades ORDER BY tipoId DESC")
    fun observeAll(): Flow<List<TipoPenalidadEntity>>
    @Query("SELECT * FROM tipospenalidades WHERE tipoId = :id")
    suspend fun getById(id: Int): TipoPenalidadEntity?
    @Upsert
    suspend fun upsert(entity: TipoPenalidadEntity)
    @Delete
    suspend fun delete(entity: TipoPenalidadEntity)
    @Query("DELETE FROM tipospenalidades WHERE tipoId = :id")
    suspend fun deleteById(id: Int)
}