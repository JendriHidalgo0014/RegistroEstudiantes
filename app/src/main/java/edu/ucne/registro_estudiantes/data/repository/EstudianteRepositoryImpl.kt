package edu.ucne.registro_estudiantes.data.repository

import edu.ucne.registro_estudiantes.data.local.dao.EstudianteDao
import edu.ucne.registro_estudiantes.data.local.mapper.toDomain
import edu.ucne.registro_estudiantes.data.local.mapper.toEntity
import edu.ucne.registro_estudiantes.domain.model.Estudiante
import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EstudianteRepositoryImpl (
    private val dao: EstudianteDao
) : EstudianteRepository {
    override fun observeEstudiantes(): Flow<List<Estudiante>> = dao.observeAll().map { list ->
        list.map {it.toDomain()}
    }

    override suspend fun getEstudiante(id: Int): Estudiante? = dao.getById(id)?.toDomain()

    override suspend fun upsert(estudiante: Estudiante): Int {
        dao.upsert(estudiante.toEntity())
        return estudiante.estudianteId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}