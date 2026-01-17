package edu.ucne.registro_estudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registro_estudiantes.presentation.estudiantetareas.edit.EditEstudianteScreen
import edu.ucne.registro_estudiantes.presentation.estudiantetareas.list.ListEstudianteScreen
import edu.ucne.registro_estudiantes.ui.theme.Registro_EstudiantesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Registro_EstudiantesTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "estudiantes_list"
                ) {
                    composable("estudiantes_list") {
                        ListEstudianteScreen(
                            onCreate = { navController.navigate("estudiantes_create") },
                            onEdit = { estudianteId ->
                                navController.navigate("estudiantes_edit/$estudianteId")
                            }
                        )
                    }
                    composable("estudiantes_create") {
                        EditEstudianteScreen(
                            estudianteId = null,
                            goBack = { navController.popBackStack() }
                        )
                    }
                    composable("estudiantes_edit/{estudianteId}") { backStackEntry ->
                        val estudianteId = backStackEntry.arguments?.getString("estudianteId")?.toInt() ?: 0
                        EditEstudianteScreen(
                            estudianteId = estudianteId,
                            goBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}