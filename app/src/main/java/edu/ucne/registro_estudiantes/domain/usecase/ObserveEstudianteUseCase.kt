package edu.ucne.registro_estudiantes.domain.usecase

import edu.ucne.registro_estudiantes.domain.model.Estudiante
import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow

class ObserveEstudianteUseCase (
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> = repository.observeEstudiantes()
}