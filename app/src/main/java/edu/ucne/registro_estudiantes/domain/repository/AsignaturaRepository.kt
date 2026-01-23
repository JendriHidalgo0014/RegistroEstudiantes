package edu.ucne.registro_estudiantes.domain.repository

import edu.ucne.registro_estudiantes.domain.model.Asignatura
import kotlinx.coroutines.flow.Flow

interface AsignaturaRepository {
    fun observeAsignaturas(): Flow<List<Asignatura>>
    suspend fun getAsignatura(id: Int): Asignatura?
    suspend fun upsert(asignatura: Asignatura): Int
    suspend fun delete(id: Int)
}