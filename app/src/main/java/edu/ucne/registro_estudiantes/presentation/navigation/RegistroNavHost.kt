package edu.ucne.registro_estudiantes.presentation.navigation

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.registro_estudiantes.presentation.estudiantetareas.edit.EditEstudianteScreen
import edu.ucne.registro_estudiantes.presentation.estudiantetareas.list.ListEstudianteScreen
import edu.ucne.registro_estudiantes.presentation.tareas.edit.EditAsignaturaScreen
import edu.ucne.registro_estudiantes.presentation.tareas.list.ListAsignaturaScreen
import edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.edit.EditTipoPenalidadScreen
import edu.ucne.registro_estudiantes.presentation.tipoPenalidadtareas.list.ListTipoPenalidadScreen
import kotlinx.coroutines.launch

@Composable
fun RegistroNavHost(
    navHostController: NavHostController
) {
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    DrawerMenu(
        drawerState = drawerState,
        navHostController = navHostController
    ) {
        NavHost(
            navController = navHostController,
            startDestination = Screen.EstudianteList
        ) {
            composable<Screen.EstudianteList> {
                ListEstudianteScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onCreate = {
                        navHostController.navigate(Screen.EstudianteEdit(0))
                    },
                    onEdit = { estudianteId ->
                        navHostController.navigate(Screen.EstudianteEdit(estudianteId))
                    }
                )
            }

            composable<Screen.EstudianteEdit> {
                val args = it.toRoute<Screen.EstudianteEdit>()
                EditEstudianteScreen(
                    estudianteId = if (args.estudianteId == 0) null else args.estudianteId,
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }

            composable<Screen.AsignaturaList> {
                ListAsignaturaScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onCreate = {
                        navHostController.navigate(Screen.AsignaturaEdit(0))
                    },
                    onEdit = { asignaturaId ->
                        navHostController.navigate(Screen.AsignaturaEdit(asignaturaId))
                    }
                )
            }

            composable<Screen.AsignaturaEdit> {
                val args = it.toRoute<Screen.AsignaturaEdit>()
                EditAsignaturaScreen(
                    asignaturaId = if (args.asignaturaId == 0) null else args.asignaturaId,
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }

            composable<Screen.TipoPenalidadList> {
                ListTipoPenalidadScreen(
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    onCreate = {
                        navHostController.navigate(Screen.TipoPenalidadEdit(0))
                    },
                    onEdit = { tipoId ->
                        navHostController.navigate(Screen.TipoPenalidadEdit(tipoId))
                    }
                )
            }

            composable<Screen.TipoPenalidadEdit> {
                val args = it.toRoute<Screen.TipoPenalidadEdit>()
                EditTipoPenalidadScreen(
                    tipoId = if (args.tipoId == 0) null else args.tipoId,
                    onDrawer = {
                        scope.launch {
                            drawerState.open()
                        }
                    },
                    goBack = {
                        navHostController.navigateUp()
                    }
                )
            }
        }
    }
}