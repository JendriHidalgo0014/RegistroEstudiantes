package edu.ucne.registro_estudiantes.domain.model

data class Estudiante(
    val estudianteId: Int = 0,
    val nombres: String,
    val email: String,
    val edad: Int
)
