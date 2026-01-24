package edu.ucne.registro_estudiantes.data.repository

import edu.ucne.registro_estudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registro_estudiantes.data.local.mapper.toDomain
import edu.ucne.registro_estudiantes.data.local.mapper.toEntity
import edu.ucne.registro_estudiantes.domain.model.Asignatura
import edu.ucne.registro_estudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AsignaturaRepositoryImpl (
    private val dao: AsignaturaDao
) : AsignaturaRepository {
    override fun observeAsignaturas(): Flow<List<Asignatura>> = dao.observeAll().map { list ->
        list.map {it.toDomain()}
    }

    override suspend fun getAsignatura(id: Int): Asignatura? = dao.getById(id)?.toDomain()

    override suspend fun upsert(asignatura: Asignatura): Int {
        dao.upsert(asignatura.toEntity())
        return asignatura.asignaturaId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}