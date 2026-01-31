package edu.ucne.registro_estudiantes.domain.model

data class TipoPenalidad(
    val tipoId: Int = 0,
    val nombre: String,
    val descripcion: String,
    val puntosdescuento: Int
)
