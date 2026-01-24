package edu.ucne.registro_estudiantes.domain.usecase.asignaturas

import edu.ucne.registro_estudiantes.domain.model.Asignatura
import edu.ucne.registro_estudiantes.domain.repository.AsignaturaRepository
import kotlinx.coroutines.flow.Flow

class ObserveAsignaturaUseCase (
    private val repository: AsignaturaRepository
) {
    operator fun invoke(): Flow<List<Asignatura>> = repository.observeAsignaturas()
}