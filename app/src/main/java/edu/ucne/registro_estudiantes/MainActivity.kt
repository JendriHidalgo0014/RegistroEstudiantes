package edu.ucne.registro_estudiantes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.registro_estudiantes.presentation.navigation.RegistroNavHost
import edu.ucne.registro_estudiantes.ui.theme.Registro_EstudiantesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Registro_EstudiantesTheme {
                val navController = rememberNavController()
                RegistroNavHost(navController)
            }
        }
    }
}