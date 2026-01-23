package edu.ucne.registro_estudiantes.domain.usecase.asignaturas

import edu.ucne.registro_estudiantes.domain.repository.AsignaturaRepository

class DeleteAsignaturaUseCase (
    private val repository: AsignaturaRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}