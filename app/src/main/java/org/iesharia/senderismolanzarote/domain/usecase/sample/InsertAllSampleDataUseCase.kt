package org.iesharia.senderismolanzarote.domain.usecase.sample

import android.util.Log
import org.iesharia.senderismolanzarote.domain.usecase.sample.route.reference.InsertSampleRouteReferenceDataUseCase
import org.iesharia.senderismolanzarote.domain.usecase.sample.user.InsertSampleUserDataUseCase
import org.iesharia.senderismolanzarote.domain.usecase.sample.route.main.InsertSampleRouteMainDataUseCase
import javax.inject.Inject

class InsertAllSampleDataUseCase @Inject constructor(
    private val insertSampleRouteReferenceDataUseCase: InsertSampleRouteReferenceDataUseCase,
    private val insertSampleUserDataUseCase: InsertSampleUserDataUseCase,
    private val insertSampleRouteMainDataUseCase: InsertSampleRouteMainDataUseCase
) {
    suspend operator fun invoke() {
        Log.d("InsertAllSampleData", "Starting data insertion")

        // El orden es importante debido a las dependencias entre tablas
        try {
            Log.d("InsertAllSampleData", "Inserting route reference data")
            insertSampleRouteReferenceDataUseCase()

            Log.d("InsertAllSampleData", "Inserting user data")
            insertSampleUserDataUseCase()

            Log.d("InsertAllSampleData", "Inserting route main data")
            insertSampleRouteMainDataUseCase()

            Log.d("InsertAllSampleData", "All sample data inserted successfully")
        } catch (e: Exception) {
            Log.e("InsertAllSampleData", "Error inserting sample data", e)
            throw e
        }
    }
}