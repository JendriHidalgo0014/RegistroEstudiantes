package edu.ucne.registro_estudiantes.domain.usecase.estudiantes

import edu.ucne.registro_estudiantes.domain.model.Estudiante
import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository

class GetEstudianteUseCase (
    private val repository: EstudianteRepository
) {
    suspend operator fun invoke(id: Int): Estudiante? = repository.getEstudiante(id)
}