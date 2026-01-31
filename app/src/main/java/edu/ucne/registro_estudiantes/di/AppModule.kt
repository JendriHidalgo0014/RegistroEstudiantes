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
import edu.ucne.registro_estudiantes.data.local.dao.TipoPenalidadDao
import edu.ucne.registro_estudiantes.data.local.database.EstudianteDatabase
import edu.ucne.registro_estudiantes.data.repository.AsignaturaRepositoryImpl
import edu.ucne.registro_estudiantes.data.repository.EstudianteRepositoryImpl
import edu.ucne.registro_estudiantes.data.repository.TipoPenalidadRepositoryImpl
import edu.ucne.registro_estudiantes.domain.repository.AsignaturaRepository
import edu.ucne.registro_estudiantes.domain.repository.EstudianteRepository
import edu.ucne.registro_estudiantes.domain.repository.TipoPenalidadRepository
import edu.ucne.registro_estudiantes.domain.usecase.UpsertAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.DeleteAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.GetAsignaturaUseCase
import edu.ucne.registro_estudiantes.domain.usecase.asignaturas.GetAsignaturasUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.DeleteEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.GetEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.GetEstudiantesUseCase
import edu.ucne.registro_estudiantes.domain.usecase.estudiantes.UpsertEstudianteUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.DeleteTipoPenalidadUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.GetTipoPenalidadUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.GetTipoPenalidadesUseCase
import edu.ucne.registro_estudiantes.domain.usecase.tipoPenalidades.UpsertTipoPenalidadUseCase
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

    @Provides
    fun provideTipoPenalidadDao(estudianteDatabase: EstudianteDatabase) = estudianteDatabase.tipoPenalidadDao()

    @Provides
    @Singleton
    fun provideTipoPenalidadRepository(dao: TipoPenalidadDao): TipoPenalidadRepository {
        return TipoPenalidadRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideGetTipoPenalidadUseCase(repository: TipoPenalidadRepository): GetTipoPenalidadUseCase {
        return GetTipoPenalidadUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTipoPenalidadesUseCase(repository: TipoPenalidadRepository): GetTipoPenalidadesUseCase {
        return GetTipoPenalidadesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpsertTipoPenalidadUseCase(repository: TipoPenalidadRepository): UpsertTipoPenalidadUseCase {
        return UpsertTipoPenalidadUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteTipoPenalidadUseCase(repository: TipoPenalidadRepository): DeleteTipoPenalidadUseCase {
        return DeleteTipoPenalidadUseCase(repository)
    }
}