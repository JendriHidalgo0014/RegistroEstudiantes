package edu.ucne.registro_estudiantes.domain.usecase.estudiantes

import edu.ucne.registro_estudiantes.domain.model.Estudiante
import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository
import kotlinx.coroutines.flow.Flow

class GetEstudiantesUseCase (
    private val repository: EstudianteRepository
) {
    operator fun invoke(): Flow<List<Estudiante>> = repository.observeEstudiantes()
}