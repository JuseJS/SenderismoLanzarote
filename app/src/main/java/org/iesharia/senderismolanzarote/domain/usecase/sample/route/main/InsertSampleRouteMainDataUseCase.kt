package org.iesharia.senderismolanzarote.domain.usecase.sample.route.main

import kotlinx.coroutines.flow.first
import org.iesharia.senderismolanzarote.domain.model.route.main.*
import org.iesharia.senderismolanzarote.domain.repository.route.main.*
import org.iesharia.senderismolanzarote.domain.repository.route.reference.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

class InsertSampleRouteMainDataUseCase @Inject constructor(
    private val routeRepository: RouteRepository,
    private val pointOfInterestRepository: PointOfInterestRepository,
    private val difficultyLevelRepository: DifficultyLevelRepository,
    private val seasonRouteRepository: SeasonRouteRepository,
    private val routeStatusRepository: RouteStatusRepository,
    private val poiTypeRepository: PoiTypeRepository,
) {
    suspend operator fun invoke() {
        // Obtener datos de referencia
        val difficulties = difficultyLevelRepository.getAllDifficultyLevels().first()
        val seasons = seasonRouteRepository.getAllSeasonRoutes().first()
        val statuses = routeStatusRepository.getAllRouteStatuses().first()
        val poiTypes = poiTypeRepository.getAllPoiTypes().first()

        // Rutas de ejemplo expandidas con datos reales
        val routes = listOf(
            RouteModel(
                id = 1,
                name = "Volcán de la Corona",
                difficultyLevelModel = difficulties.find { it.id == 3 }!!, // Difícil
                season = seasons.find { it.id == 2 }!!, // Octubre-Mayo
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("29.1553"),
                startLongitude = BigDecimal("-13.4419"),
                endLatitude = BigDecimal("29.1553"),
                endLongitude = BigDecimal("-13.4419"),
                distanceKm = BigDecimal("9.5"),
                estimatedDuration = LocalTime.of(3, 30),
                elevationGain = 485,
                description = "Ruta circular que asciende al impresionante Volcán de la Corona, atravesando paisajes volcánicos únicos con vistas al Archipiélago Chinijo. Se puede observar la formación geológica más antigua de Lanzarote y el túnel volcánico más largo de Europa.",
                startPoint = "Ye",
                endPoint = "Ye",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Bueno",
                isCircular = true
            ),
            RouteModel(
                id = 2,
                name = "Caldera Blanca",
                difficultyLevelModel = difficulties.find { it.id == 2 }!!, // Moderado
                season = seasons.find { it.id == 1 }!!, // Todo el año
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("29.0075"),
                startLongitude = BigDecimal("-13.7500"),
                endLatitude = BigDecimal("29.0075"),
                endLongitude = BigDecimal("-13.7500"),
                distanceKm = BigDecimal("10.2"),
                estimatedDuration = LocalTime.of(4, 0),
                elevationGain = 256,
                description = "Ruta circular alrededor del cráter más grande de Lanzarote. Ofrece vistas panorámicas del Parque Nacional de Timanfaya y la posibilidad de rodear completamente el cráter por su borde superior.",
                startPoint = "Mancha Blanca",
                endPoint = "Mancha Blanca",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Excelente",
                isCircular = true
            ),
            RouteModel(
                id = 3,
                name = "Risco de Famara",
                difficultyLevelModel = difficulties.find { it.id == 4 }!!, // Muy difícil
                season = seasons.find { it.id == 2 }!!, // Octubre-Mayo
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("29.2079"),
                startLongitude = BigDecimal("-13.4291"),
                endLatitude = BigDecimal("29.1741"),
                endLongitude = BigDecimal("-13.4338"),
                distanceKm = BigDecimal("12.8"),
                estimatedDuration = LocalTime.of(6, 0),
                elevationGain = 671,
                description = "Impresionante ruta por el acantilado más alto de Lanzarote, con vistas espectaculares a La Graciosa y el Archipiélago Chinijo. Incluye el paso por el histórico Mirador del Río.",
                startPoint = "Ye",
                endPoint = "Teguise",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Moderado",
                isCircular = false
            ),
            RouteModel(
                id = 4,
                name = "Playas de Papagayo",
                difficultyLevelModel = difficulties.find { it.id == 1 }!!, // Fácil
                season = seasons.find { it.id == 1 }!!, // Todo el año
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("28.8441"),
                startLongitude = BigDecimal("-13.7820"),
                endLatitude = BigDecimal("28.8441"),
                endLongitude = BigDecimal("-13.7820"),
                distanceKm = BigDecimal("5.2"),
                estimatedDuration = LocalTime.of(2, 0),
                elevationGain = 89,
                description = "Ruta costera que recorre las playas más hermosas de Lanzarote, incluyendo Papagayo, Mujeres y Pozo. Ideal para familias y fotografía paisajística.",
                startPoint = "Playa Blanca",
                endPoint = "Playa Blanca",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Excelente",
                isCircular = true
            ),
            RouteModel(
                id = 5,
                name = "Montaña Roja",
                difficultyLevelModel = difficulties.find { it.id == 2 }!!, // Moderado
                season = seasons.find { it.id == 1 }!!, // Todo el año
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("28.8589"),
                startLongitude = BigDecimal("-13.8647"),
                endLatitude = BigDecimal("28.8589"),
                endLongitude = BigDecimal("-13.8647"),
                distanceKm = BigDecimal("6.3"),
                estimatedDuration = LocalTime.of(2, 30),
                elevationGain = 171,
                description = "Ascensión al volcán más meridional de Lanzarote, con vistas espectaculares de Playa Blanca y Fuerteventura. Zona protegida por su valor ornitológico.",
                startPoint = "Playa Blanca",
                endPoint = "Playa Blanca",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Bueno",
                isCircular = true
            ),
            RouteModel(
                id = 6,
                name = "Los Helechos",
                difficultyLevelModel = difficulties.find { it.id == 2 }!!, // Moderado
                season = seasons.find { it.id == 2 }!!, // Octubre-Mayo
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("29.0789"),
                startLongitude = BigDecimal("-13.5894"),
                endLatitude = BigDecimal("29.0789"),
                endLongitude = BigDecimal("-13.5894"),
                distanceKm = BigDecimal("8.7"),
                estimatedDuration = LocalTime.of(3, 15),
                elevationGain = 312,
                description = "Ruta circular por el volcán Los Helechos, con vistas al valle de Haría y sus mil palmeras. Incluye zonas de vegetación endémica y antiguas zonas de cultivo.",
                startPoint = "Haría",
                endPoint = "Haría",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Bueno",
                isCircular = true
            ),
            RouteModel(
                id = 7,
                name = "Monte Corona",
                difficultyLevelModel = difficulties.find { it.id == 3 }!!, // Difícil
                season = seasons.find { it.id == 2 }!!, // Octubre-Mayo
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("29.1725"),
                startLongitude = BigDecimal("-13.4997"),
                endLatitude = BigDecimal("29.1725"),
                endLongitude = BigDecimal("-13.4997"),
                distanceKm = BigDecimal("11.2"),
                estimatedDuration = LocalTime.of(4, 30),
                elevationGain = 448,
                description = "Ascensión al Monte Corona con vistas panorámicas de 360° de la isla. Incluye paso por antiguos hornos de cal y zonas de malpaís.",
                startPoint = "Ye",
                endPoint = "Ye",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Moderado",
                isCircular = true
            ),
            RouteModel(
                id = 8,
                name = "Sendero de los Gracioseros",
                difficultyLevelModel = difficulties.find { it.id == 3 }!!, // Difícil
                season = seasons.find { it.id == 2 }!!, // Octubre-Mayo
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("29.2154"),
                startLongitude = BigDecimal("-13.4291"),
                endLatitude = BigDecimal("29.2270"),
                endLongitude = BigDecimal("-13.4307"),
                distanceKm = BigDecimal("4.8"),
                estimatedDuration = LocalTime.of(2, 45),
                elevationGain = 526,
                description = "Histórico sendero utilizado por los habitantes de La Graciosa para llegar a Lanzarote. Descenso por el Risco de Famara con vistas espectaculares.",
                startPoint = "Mirador del Río",
                endPoint = "Playa del Risco",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Regular",
                isCircular = false
            ),
            RouteModel(
                id = 9,
                name = "Camino de los Volcanes",
                difficultyLevelModel = difficulties.find { it.id == 3 }!!, // Difícil
                season = seasons.find { it.id == 2 }!!, // Octubre-Mayo
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("29.0516"),
                startLongitude = BigDecimal("-13.7372"),
                endLatitude = BigDecimal("28.9943"),
                endLongitude = BigDecimal("-13.7497"),
                distanceKm = BigDecimal("13.5"),
                estimatedDuration = LocalTime.of(5, 0),
                elevationGain = 423,
                description = "Travesía por el corazón del Parque Nacional de Timanfaya, atravesando el Mar de Lava y múltiples volcanes históricos. Requiere permiso especial.",
                startPoint = "Mancha Blanca",
                endPoint = "Yaiza",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Bueno",
                isCircular = false
            ),
            RouteModel(
                id = 10,
                name = "Barranco del Quíquere",
                difficultyLevelModel = difficulties.find { it.id == 2 }!!, // Moderado
                season = seasons.find { it.id == 1 }!!, // Todo el año
                status = statuses.find { it.id == 1 }!!, // Abierta
                startLatitude = BigDecimal("28.9089"),
                startLongitude = BigDecimal("-13.8183"),
                endLatitude = BigDecimal("28.9089"),
                endLongitude = BigDecimal("-13.8183"),
                distanceKm = BigDecimal("7.4"),
                estimatedDuration = LocalTime.of(3, 0),
                elevationGain = 245,
                description = "Ruta costera que recorre el espectacular barranco del Quíquere, con vistas a los Ajaches y acceso a calas vírgenes.",
                startPoint = "Femés",
                endPoint = "Femés",
                lastReviewDate = LocalDate.now(),
                statusSignage = "Bueno",
                isCircular = true
            )
        )

        // Insertar rutas
        routes.forEach { routeRepository.insertRoute(it) }

        // Crear y añadir punto de interés
        val pointsOfInterest = listOf(

            // Ruta 1: Volcán de la Corona
            PointOfInterestModel(
                id = 1,
                routeModel = routes.find { it.id == 1 }!!,
                name = "Cima del Volcán de la Corona",
                type = poiTypes.find { it.id == 1 }!!,
                description = "Punto más alto del volcán, con vistas panorámicas.",
                latitude = BigDecimal("29.1553"),
                longitude = BigDecimal("-13.4419"),
                estimatedVisitTime = 30
            ),
            PointOfInterestModel(
                id = 2,
                routeModel = routes.find { it.id == 1 }!!,
                name = "Malpaís de la Corona",
                type = poiTypes.find { it.id == 11 }!!,
                description = "Extenso campo de lava al pie del volcán.",
                latitude = BigDecimal("29.1650"),
                longitude = BigDecimal("-13.4500"),
                estimatedVisitTime = 45
            ),
            PointOfInterestModel(
                id = 3,
                routeModel = routes.find { it.id == 1 }!!,
                name = "Mirador del Río (Vista Lejana)",
                type = poiTypes.find { it.id == 3 }!!,
                description = "Vista lejana del Mirador del Río desde la ruta.",
                latitude = BigDecimal("29.1450"),
                longitude = BigDecimal("-13.4300"),
                estimatedVisitTime = 20
            ),

            // Ruta 2: Caldera Blanca
            PointOfInterestModel(
                id = 4,
                routeModel = routes.find { it.id == 2 }!!,
                name = "Mirador de Caldera Blanca",
                type = poiTypes.find { it.id == 3 }!!,
                description = "Punto con vistas al interior del cráter.",
                latitude = BigDecimal("29.0075"),
                longitude = BigDecimal("-13.7500"),
                estimatedVisitTime = 45
            ),
            PointOfInterestModel(
                id = 5,
                routeModel = routes.find { it.id == 2 }!!,
                name = "Borde del cráter",
                type = poiTypes.find { it.id == 1 }!!,
                description = "Recorrido por el borde superior del cráter.",
                latitude = BigDecimal("29.0150"),
                longitude = BigDecimal("-13.7450"),
                estimatedVisitTime = 60
            ),
            PointOfInterestModel(
                id = 6,
                routeModel = routes.find { it.id == 2 }!!,
                name = "Antigua cantera",
                type = poiTypes.find { it.id == 2 }!!,
                description = "Restos de una antigua cantera en las faldas de la Caldera Blanca.",
                latitude = BigDecimal("29.0000"),
                longitude = BigDecimal("-13.7600"),
                estimatedVisitTime = 30
            ),

            // Ruta 3: Risco de Famara
            PointOfInterestModel(
                id = 7,
                routeModel = routes.find { it.id == 3 }!!,
                name = "Mirador del Río",
                type = poiTypes.find { it.id == 3 }!!,
                description = "Obra de César Manrique con vistas a La Graciosa.",
                latitude = BigDecimal("29.2154"),
                longitude = BigDecimal("-13.4291"),
                estimatedVisitTime = 60
            ),
            PointOfInterestModel(
                id = 8,
                routeModel = routes.find { it.id == 3 }!!,
                name = "Ermita de las Nieves",
                type = poiTypes.find { it.id == 2 }!!,
                description = "Pequeña ermita en lo alto del Risco.",
                latitude = BigDecimal("29.1900"),
                longitude = BigDecimal("-13.4200"),
                estimatedVisitTime = 30
            ),
            PointOfInterestModel(
                id = 9,
                routeModel = routes.find { it.id == 3 }!!,
                name = "Vistas a la Playa de Famara",
                type = poiTypes.find { it.id == 5 }!!,
                description = "Panorámica de la playa desde el Risco.",
                latitude = BigDecimal("29.1800"),
                longitude = BigDecimal("-13.4400"),
                estimatedVisitTime = 20
            ),

            // Ruta 4: Playas de Papagayo
            PointOfInterestModel(
                id = 10,
                routeModel = routes.find { it.id == 4 }!!,
                name = "Playa de Papagayo",
                type = poiTypes.find { it.id == 5 }!!,
                description = "Playa de arena blanca y aguas cristalinas.",
                latitude = BigDecimal("28.8441"),
                longitude = BigDecimal("-13.7820"),
                estimatedVisitTime = 90
            ),
            PointOfInterestModel(
                id = 11,
                routeModel = routes.find { it.id == 4 }!!,
                name = "Punta de Papagayo",
                type = poiTypes.find { it.id == 3 }!!,
                description = "Extremo sur de Lanzarote con vistas a Fuerteventura.",
                latitude = BigDecimal("28.8350"),
                longitude = BigDecimal("-13.7750"),
                estimatedVisitTime = 30
            ),
            PointOfInterestModel(
                id = 12,
                routeModel = routes.find { it.id == 4 }!!,
                name = "Caleta del Congrio",
                type = poiTypes.find { it.id == 5 }!!,
                description = "Pequeña cala protegida del viento.",
                latitude = BigDecimal("28.8500"),
                longitude = BigDecimal("-13.7900"),
                estimatedVisitTime = 45
            ),

            // Ruta 5: Montaña Roja
            PointOfInterestModel(
                id = 13,
                routeModel = routes.find { it.id == 5 }!!,
                name = "Cima de Montaña Roja",
                type = poiTypes.find { it.id == 1 }!!,
                description = "Vistas panorámicas de Playa Blanca y Fuerteventura.",
                latitude = BigDecimal("28.8589"),
                longitude = BigDecimal("-13.8647"),
                estimatedVisitTime = 45
            ),
            PointOfInterestModel(
                id = 14,
                routeModel = routes.find { it.id == 5 }!!,
                name = "Mirador a Playa Blanca",
                type = poiTypes.find { it.id == 3 }!!,
                description = "Vista de Playa Blanca desde la ladera de la montaña.",
                latitude = BigDecimal("28.8650"),
                longitude = BigDecimal("-13.8700"),
                estimatedVisitTime = 20
            ),
            PointOfInterestModel(
                id = 15,
                routeModel = routes.find { it.id == 5 }!!,
                name = "Crater de Montaña Roja",
                type = poiTypes.find { it.id == 1 }!!,
                description = "Interior del crater de Montaña Roja",
                latitude = BigDecimal("28.8550"),
                longitude = BigDecimal("-13.8600"),
                estimatedVisitTime = 30
            ),

            // Ruta 6: Los Helechos
            PointOfInterestModel(
                id = 16,
                routeModel = routes.find { it.id == 6 }!!,
                name = "Valle de Haría",
                type = poiTypes.find { it.id == 3 }!!,
                description = "Vista panorámica del valle de Haría.",
                latitude = BigDecimal("29.0850"),
                longitude = BigDecimal("-13.5900"),
                estimatedVisitTime = 30
            ),
            PointOfInterestModel(
                id = 17,
                routeModel = routes.find { it.id == 6 }!!,
                name = "Vegetación Endémica",
                type = poiTypes.find { it.id == 4 }!!,
                description = "Zona con presencia de flora endémica canaria.",
                latitude = BigDecimal("29.0750"),
                longitude = BigDecimal("-13.5800"),
                estimatedVisitTime = 45
            ),
            PointOfInterestModel(
                id = 18,
                routeModel = routes.find { it.id == 6 }!!,
                name = "Antiguas zonas de Cultivo",
                type = poiTypes.find { it.id == 9 }!!,
                description = "Terrazas de cultivo abandonadas.",
                latitude = BigDecimal("29.0700"),
                longitude = BigDecimal("-13.5950"),
                estimatedVisitTime = 25
            ),

            // Ruta 7: Monte Corona
            PointOfInterestModel(
                id = 19,
                routeModel = routes.find { it.id == 7 }!!,
                name = "Cima de Monte Corona",
                type = poiTypes.find { it.id == 1 }!!,
                description = "Punto más alto del volcán.",
                latitude = BigDecimal("29.1725"),
                longitude = BigDecimal("-13.4997"),
                estimatedVisitTime = 60
            ),
            PointOfInterestModel(
                id = 20,
                routeModel = routes.find { it.id == 7 }!!,
                name = "Hornos de Cal",
                type = poiTypes.find { it.id == 2 }!!,
                description = "Restos de antiguos hornos utilizados para la producción de cal.",
                latitude = BigDecimal("29.1650"),
                longitude = BigDecimal("-13.5050"),
                estimatedVisitTime = 30
            ),
            PointOfInterestModel(
                id = 21,
                routeModel = routes.find { it.id == 7 }!!,
                name = "Malpaís del Volcán",
                type = poiTypes.find { it.id == 11 }!!,
                description = "Paisaje de malpaís alrededor del volcán.",
                latitude = BigDecimal("29.1800"),
                longitude = BigDecimal("-13.5100"),
                estimatedVisitTime = 40
            ),


            // Ruta 8: Sendero de los Gracioseros
            PointOfInterestModel(
                id = 22,
                routeModel = routes.find { it.id == 8 }!!,
                name = "Mirador del Río",
                type = poiTypes.find { it.id == 3 }!!,
                description = "Inicio del sendero junto al Mirador del Río.",
                latitude = BigDecimal("29.2154"),
                longitude = BigDecimal("-13.4291"),
                estimatedVisitTime = 30
            ),
            PointOfInterestModel(
                id = 23,
                routeModel = routes.find { it.id == 8 }!!,
                name = "Risco de Famara (Descenso)",
                type = poiTypes.find { it.id == 1 }!!,
                description = "Tramo de descenso por el acantilado.",
                latitude = BigDecimal("29.2200"),
                longitude = BigDecimal("-13.4300"),
                estimatedVisitTime = 75
            ),
            PointOfInterestModel(
                id = 24,
                routeModel = routes.find { it.id == 8 }!!,
                name = "Playa del Risco",
                type = poiTypes.find { it.id == 5 }!!,
                description = "Llegada a la playa al final del sendero.",
                latitude = BigDecimal("29.2270"),
                longitude = BigDecimal("-13.4307"),
                estimatedVisitTime = 60
            ),

            // Ruta 9: Camino de los Volcanes
            PointOfInterestModel(
                id = 25,
                routeModel = routes.find { it.id == 9 }!!,
                name = "Centro de Visitantes de Timanfaya (inicio)",
                type = poiTypes.find { it.id == 2 }!!,
                description = "Punto de partida y obtención de permisos.",
                latitude = BigDecimal("29.0516"),
                longitude = BigDecimal("-13.7372"),
                estimatedVisitTime = 45
            ),
            PointOfInterestModel(
                id = 26,
                routeModel = routes.find { it.id == 9 }!!,
                name = "Mar de Lava",
                type = poiTypes.find { it.id == 11 }!!,
                description = "Extensa área de lava solidificada.",
                latitude = BigDecimal("29.0200"),
                longitude = BigDecimal("-13.7400"),
                estimatedVisitTime = 90
            ),
            PointOfInterestModel(
                id = 27,
                routeModel = routes.find { it.id == 9 }!!,
                name = "Volcanes históricos",
                type = poiTypes.find { it.id == 1 }!!,
                description = "Vista de diferentes conos volcánicos.",
                latitude = BigDecimal("28.9943"),
                longitude = BigDecimal("-13.7497"),
                estimatedVisitTime = 60
            ),

            // Ruta 10: Barranco del Quíquere
            PointOfInterestModel(
                id = 28,
                routeModel = routes.find { it.id == 10 }!!,
                name = "Femés (inicio del sendero)",
                type = poiTypes.find { it.id == 2 }!!,
                description = "Punto de partida en el pueblo de Femés.",
                latitude = BigDecimal("28.9089"),
                longitude = BigDecimal("-13.8183"),
                estimatedVisitTime = 20
            ),
            PointOfInterestModel(
                id = 29,
                routeModel = routes.find { it.id == 10 }!!,
                name = "Barranco del Quíquere",
                type = poiTypes.find { it.id == 11 }!!,
                description = "Recorrido por el cauce del barranco.",
                latitude = BigDecimal("28.9150"),
                longitude = BigDecimal("-13.8100"),
                estimatedVisitTime = 75
            ),
            PointOfInterestModel(
                id = 30,
                routeModel = routes.find { it.id == 10 }!!,
                name = "Calas Vírgenes",
                type = poiTypes.find { it.id == 5 }!!,
                description = "Acceso a calas poco frecuentadas.",
                latitude = BigDecimal("28.9200"),
                longitude = BigDecimal("-13.8250"),
                estimatedVisitTime = 60
            )
        )

        // Insertar punto de interés
        pointsOfInterest.forEach { pointOfInterestRepository.insertPointOfInterest(it) }
    }
}
