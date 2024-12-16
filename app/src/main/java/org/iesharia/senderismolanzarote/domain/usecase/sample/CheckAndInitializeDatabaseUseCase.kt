package org.iesharia.senderismolanzarote.domain.usecase.sample

import android.util.Log
import kotlinx.coroutines.flow.first
import org.iesharia.senderismolanzarote.data.database.AppDatabase
import org.iesharia.senderismolanzarote.data.datastore.AppDataStore
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository
import javax.inject.Inject

class CheckAndInitializeDatabaseUseCase @Inject constructor(
    private val appDataStore: AppDataStore,
    private val difficultyLevelRepository: DifficultyLevelRepository,
    private val insertAllSampleDataUseCase: InsertAllSampleDataUseCase
) {
    suspend operator fun invoke() {
        Log.d("CheckAndInitDB", "Checking initialization status")

        // Establecemos como que no esta iniciada
        //appDataStore.setNotInitialized()

        // Verificar si ya está inicializada
        Log.d("CheckAndInitDB", appDataStore.isInitialized.first().toString())
        if (appDataStore.isInitialized.first()) {
            Log.d("CheckAndInitDB", "Database already initialized")
            return
        }

        // Verificar si hay datos en la base de datos
        val existingData = difficultyLevelRepository.getAllDifficultyLevels().first()
        if (existingData.isNotEmpty()) {
            Log.d("CheckAndInitDB", "Data exists, marking as initialized")
            appDataStore.setInitialized()
            return
        }

        // Insertar datos de muestra
        Log.d("CheckAndInitDB", "Inserting sample data")
        insertAllSampleDataUseCase()
        appDataStore.setInitialized()
        Log.d("CheckAndInitDB", "Sample data inserted and initialization completed")
    }
}