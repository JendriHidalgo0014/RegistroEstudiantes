package edu.ucne.registro_estudiantes.domain.usecase.asignaturas
private const val Campo = "Este campo es obligatorio"
data class AsignaturaValidationResult(
    val isValid: Boolean,
    val error: String? = null
)
fun validateCodigo(value: String): AsignaturaValidationResult {
    if (value.isBlank()) return AsignaturaValidationResult(false, Campo)
    return AsignaturaValidationResult(true)
}

fun validateNombre(value: String): AsignaturaValidationResult {
    if (value.isBlank()) return AsignaturaValidationResult(false, Campo)
    return AsignaturaValidationResult(true)
}

fun validateAula(value: String): AsignaturaValidationResult {
    if (value.isBlank()) return AsignaturaValidationResult(false, Campo)
    return AsignaturaValidationResult(true)
}

fun validateCreditos(value: String): AsignaturaValidationResult {
    if (value.isBlank()) return AsignaturaValidationResult(false, Campo)
    return AsignaturaValidationResult(true)
}

fun validateNombreDuplicado(nombre: String, nombresExistentes: List<String>): AsignaturaValidationResult {
    if (nombresExistentes.contains(nombre)) {
        return AsignaturaValidationResult(false, "Esta asignatura ya está registrada")
    }
    return AsignaturaValidationResult(true)
}