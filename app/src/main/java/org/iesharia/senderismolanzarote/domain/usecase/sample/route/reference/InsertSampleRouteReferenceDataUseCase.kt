package org.iesharia.senderismolanzarote.domain.usecase.sample.route.reference

import org.iesharia.senderismolanzarote.domain.model.route.reference.*
import org.iesharia.senderismolanzarote.domain.repository.route.reference.*
import javax.inject.Inject

class InsertSampleRouteReferenceDataUseCase @Inject constructor(
    private val difficultyLevelRepository: DifficultyLevelRepository,
    private val seasonRouteRepository: SeasonRouteRepository,
    private val routeStatusRepository: RouteStatusRepository,
    private val poiTypeRepository: PoiTypeRepository,
) {
    suspend operator fun invoke() {
        // Niveles de dificultad con descripciones específicas de Lanzarote
        val difficulties = listOf(
            DifficultyLevelModel(
                id = 1,
                name = "Fácil",
                description = "Senderos bien marcados y mantenidos, con poco desnivel. Ideal para familias y principiantes. Ejemplos: Paseo costero de Papagayo, Charco de los Clicos, Salinas de Janubio."
            ),
            DifficultyLevelModel(
                id = 2,
                name = "Moderado",
                description = "Senderos con desniveles moderados y terreno variado. Requiere cierta forma física. Ejemplos: Caldera Blanca, Montaña Roja, Los Helechos."
            ),
            DifficultyLevelModel(
                id = 3,
                name = "Difícil",
                description = "Rutas exigentes con desniveles importantes y terreno irregular. Requiere buena forma física. Ejemplos: Volcán de la Corona, Monte Corona, Pico Redondo."
            ),
            DifficultyLevelModel(
                id = 4,
                name = "Muy difícil",
                description = "Rutas muy exigentes con grandes desniveles, terreno técnico y exposición. Para senderistas experimentados. Ejemplos: Risco de Famara, Atravesada del Parque Nacional, Peñas del Chache."
            )
        )

        // Temporadas adaptadas al clima de Lanzarote
        val seasons = listOf(
            SeasonRouteModel(
                id = 1,
                season = "Todo el año",
                description = "Rutas costeras o protegidas del viento que se pueden realizar en cualquier época"
            ),
            SeasonRouteModel(
                id = 2,
                season = "Octubre-Mayo",
                description = "Temporada principal de senderismo, con temperaturas más suaves y mejores condiciones"
            ),
            SeasonRouteModel(
                id = 3,
                season = "Invierno (Diciembre-Febrero)",
                description = "Mejor época para rutas largas y exigentes por las temperaturas más frescas"
            ),
            SeasonRouteModel(
                id = 4,
                season = "Primavera (Marzo-Mayo)",
                description = "Época ideal para observar la floración de especies endémicas como la Siempreviva"
            ),
            SeasonRouteModel(
                id = 5,
                season = "Verano (Junio-Septiembre)",
                description = "Recomendado solo para rutas cortas, costeras o temprano en la mañana debido al calor"
            ),
            SeasonRouteModel(
                id = 6,
                season = "Otoño (Septiembre-Noviembre)",
                description = "Excelente época para senderismo con temperaturas moderadas y menos turistas"
            )
        )

        // Estados de ruta específicos para Lanzarote
        val statuses = listOf(
            RouteStatusModel(
                id = 1,
                status = "Abierta",
                description = "Ruta completamente accesible y en buenas condiciones"
            ),
            RouteStatusModel(
                id = 2,
                status = "Mantenimiento",
                description = "En mantenimiento por el Cabildo de Lanzarote o Parque Nacional"
            ),
            RouteStatusModel(
                id = 3,
                status = "Cerrada",
                description = "Temporalmente cerrada por motivos de seguridad o conservación"
            ),
            RouteStatusModel(
                id = 4,
                status = "Precaución",
                description = "Abierta pero con puntos que requieren precaución extra por desprendimientos o erosión"
            ),
            RouteStatusModel(
                id = 5,
                status = "Alerta por calima",
                description = "Afectada por calima o vientos fuertes del Sáhara. Se recomienda evitar"
            ),
            RouteStatusModel(
                id = 6,
                status = "Restricción medioambiental",
                description = "Acceso limitado por protección de especies o nidificación de aves"
            ),
            RouteStatusModel(
                id = 7,
                status = "Obras cercanas",
                description = "Afectada por obras de infraestructura o acondicionamiento cercanas"
            )
        )

        // Tipos de puntos de interés específicos de Lanzarote
        val poiTypes = listOf(
            PoiTypeModel(
                id = 1,
                type = "Volcán",
                description = "Conos y cráteres volcánicos característicos de la isla"
            ),
            PoiTypeModel(
                id = 2,
                type = "Sitio histórico",
                description = "Lugares de interés histórico como castillos, molinos o yacimientos"
            ),
            PoiTypeModel(
                id = 3,
                type = "Mirador",
                description = "Puntos con vistas panorámicas acondicionados para visitantes"
            ),
            PoiTypeModel(
                id = 4,
                type = "Flora endémica",
                description = "Zonas con vegetación única de Lanzarote como la Siempreviva o el Bejeque"
            ),
            PoiTypeModel(
                id = 5,
                type = "Playa o cala",
                description = "Zonas costeras de interés paisajístico o recreativo"
            ),
            PoiTypeModel(
                id = 6,
                type = "Obra de Manrique",
                description = "Intervenciones artísticas de César Manrique en el paisaje"
            ),
            PoiTypeModel(
                id = 7,
                type = "Tubo volcánico",
                description = "Formaciones volcánicas subterráneas como Cueva de los Verdes o Jameos"
            ),
            PoiTypeModel(
                id = 8,
                type = "Salinas",
                description = "Salinas tradicionales como las de Janubio u Órzola"
            ),
            PoiTypeModel(
                id = 9,
                type = "Viñedo La Geria",
                description = "Cultivos tradicionales en hoyos sobre ceniza volcánica"
            ),
            PoiTypeModel(
                id = 10,
                type = "Zona arqueológica",
                description = "Yacimientos y restos arqueológicos de antiguos pobladores"
            ),
            PoiTypeModel(
                id = 11,
                type = "Malpaís",
                description = "Campos de lava históricos con formas características"
            ),
            PoiTypeModel(
                id = 12,
                type = "Fauna protegida",
                description = "Zonas de observación de especies protegidas como la Hubara"
            )
        )

        // Insertar todos los datos
        difficulties.forEach { difficultyLevelRepository.insertDifficultyLevel(it) }
        seasons.forEach { seasonRouteRepository.insertSeasonRoute(it) }
        statuses.forEach { routeStatusRepository.insertRouteStatus(it) }
        poiTypes.forEach { poiTypeRepository.insertPoiType(it) }
    }
}