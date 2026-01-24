package edu.ucne.registro_estudiantes.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.registro_estudiantes.data.local.dao.AsignaturaDao
import edu.ucne.registro_estudiantes.data.local.dao.EstudianteDao
import edu.ucne.registro_estudiantes.data.local.database.EstudianteDatabase
import edu.ucne.registro_estudiantes.data.repository.AsignaturaRepositoryImpl
import edu.ucne.registro_estudiantes.data.repository.EstudianteRepositoryImpl
import edu.ucne.registro_estudiantes.domain.repository.AsignaturaRepository
import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository
import edu.ucne.registro_estudiantes.domain.usecase.DeleteEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.GetEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.GetEstudiantesUseCase
import edu.ucne.registro_estudiantes.domain.usecase.UpsertAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.UpsertEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.DeleteAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.GetAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.GetAsignaturasUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {
    @Provides
    @Singleton
    fun provideEstudianteDatabase(@ApplicationContext appContext: Context): EstudianteDatabase {
        return Room.databaseBuilder(
            appContext,
            EstudianteDatabase::class.java,
            "Estudiante.Database"
        ).fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideEstudianteDao(estudianteDatabase: EstudianteDatabase) = estudianteDatabase.estudianteDao()

    @Provides
    @Singleton
    fun provideEstudianteRepository(dao: EstudianteDao): EstudianteRepository {
        return EstudianteRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetEstudianteUseCase(repository: EstudianteRepository): GetEstudianteUseCase {
        return GetEstudianteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetEstudiantesUseCase(repository: EstudianteRepository): GetEstudiantesUseCase {
        return GetEstudiantesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertEstudianteUseCase(repository: EstudianteRepository): UpsertEstudianteUseCase {
        return UpsertEstudianteUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteEstudianteUseCase(repository: EstudianteRepository): DeleteEstudianteUseCase {
        return DeleteEstudianteUseCase(repository)
    }

    @Provides
    fun provideAsignaturaDao(estudianteDatabase: EstudianteDatabase) = estudianteDatabase.asignaturaDao()

    @Provides
    @Singleton
    fun provideAsignaturaRepository(dao: AsignaturaDao): AsignaturaRepository {
        return AsignaturaRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetAsignaturaUseCase(repository: AsignaturaRepository): GetAsignaturaUseCase {
        return GetAsignaturaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAsignaturasUseCase(repository: AsignaturaRepository): GetAsignaturasUseCase {
        return GetAsignaturasUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertAsignaturaUseCase(repository: AsignaturaRepository): UpsertAsignaturaUseCase {
        return UpsertAsignaturaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteAsignaturaUseCase(repository: AsignaturaRepository): DeleteAsignaturaUseCase {
        return DeleteAsignaturaUseCase(repository)
    }
}