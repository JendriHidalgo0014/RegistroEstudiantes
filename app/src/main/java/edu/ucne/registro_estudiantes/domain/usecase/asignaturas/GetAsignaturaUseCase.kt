package edu.ucne.registro_estudiantes.domain.usecase.asignaturas

import edu.ucne.registro_estudiantes.domain.model.Asignatura
import edu.ucne.registro_estudiantes.domain.repository.AsignaturaRepository

class GetAsignaturaUseCase (
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int): Asignatura? = repository.getAsignatura(id)
}