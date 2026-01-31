package edu.ucne.registro_estudiantes.domain.repository

import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad
import kotlinx.coroutines.flow.Flow

interface TipoPenalidadRepository {
    fun observeTipoPenalidades(): Flow<List<TipoPenalidad>>
    suspend fun getTipoPenalidad(id: Int): TipoPenalidad?
    suspend fun upsert(tipoPenalidad: TipoPenalidad): Int
    suspend fun delete(id: Int)
}