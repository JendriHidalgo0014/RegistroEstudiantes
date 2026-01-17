package edu.ucne.registro_estudiantes.domain.usecase

data class ValidationResult (
    val isValid: Boolean,
    val error: String? = null
)

fun validateNombres(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Este campo es obligatorio")
    return ValidationResult(true)
}

fun validateEmail(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Este campo es obligatorio")
    return ValidationResult(true)
}

fun validateEdad(value: String): ValidationResult {
    if (value.isBlank()) return ValidationResult(false, "Este campo es obligatorio")
    return ValidationResult(true)
}

fun validateNombreDuplicado(nombre: String, nombresExistentes: List<String>): ValidationResult {
    if (nombresExistentes.contains(nombre)) {
        return ValidationResult(false, "Ese nombre ya esta registrado")
    }
    return ValidationResult(true)
}