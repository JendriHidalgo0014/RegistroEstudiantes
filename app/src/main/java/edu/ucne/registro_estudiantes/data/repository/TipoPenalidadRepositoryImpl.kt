package edu.ucne.registro_estudiantes.data.repository

import edu.ucne.registro_estudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registro_estudiantes.data.local.mapper.toDomain
import edu.ucne.registro_estudiantes.data.local.mapper.toEntity
import edu.ucne.registro_estudiantes.domain.model.TipoPenalidad
import edu.ucne.registro_estudiantes.domain.repository.TipoPenalidadRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TipoPenalidadRepositoryImpl @Inject constructor(
    private val dao: TipoPenalidadDao
) : TipoPenalidadRepository {
    override fun observeTipoPenalidades(): Flow<List<TipoPenalidad>> = dao.observeAll().map { list ->
        list.map {it.toDomain()}
    }

    override suspend fun getTipoPenalidad(id: Int): TipoPenalidad? = dao.getById(id)?.toDomain()

    override suspend fun upsert(tipoPenalidad: TipoPenalidad): Int {
        dao.upsert(tipoPenalidad.toEntity())
        return tipoPenalidad.tipoId
    }

    override suspend fun delete(id: Int) {
        dao.deleteById(id)
    }
}