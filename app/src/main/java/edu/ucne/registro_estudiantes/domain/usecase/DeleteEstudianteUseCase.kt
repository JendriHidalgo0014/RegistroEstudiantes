package edu.ucne.registro_estudiantes.domain.usecase

import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository

class DeleteEstudianteUseCase (
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int) = repository.delete(id)
}