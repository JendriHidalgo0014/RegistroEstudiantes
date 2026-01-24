package edu.ucne.registro_estudiantes.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object EstudianteList : Screen()

    @Serializable
    data class EstudianteEdit(val estudianteId: Int) : Screen()

    @Serializable
    data object AsignaturaList : Screen()

    @Serializable
    data class AsignaturaEdit(val asignaturaId: Int) : Screen()
}