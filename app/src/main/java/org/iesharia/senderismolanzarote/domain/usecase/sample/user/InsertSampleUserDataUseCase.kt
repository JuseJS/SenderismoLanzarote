package org.iesharia.senderismolanzarote.domain.usecase.sample.user

import org.iesharia.senderismolanzarote.domain.model.user.*
import org.iesharia.senderismolanzarote.domain.repository.user.*
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.inject.Inject
import at.favre.lib.crypto.bcrypt.BCrypt
import kotlinx.coroutines.flow.first
import org.iesharia.senderismolanzarote.domain.repository.route.reference.DifficultyLevelRepository

class InsertSampleUserDataUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userRoleRepository: UserRoleRepository,
    private val difficultyLevelRepository: DifficultyLevelRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) {
    suspend operator fun invoke() {
        val difficulties = difficultyLevelRepository.getAllDifficultyLevels().first()

        // Roles de usuario
        val roles = listOf(
            UserRoleModel(
                id = 1,
                name = "Usuario",
                description = "Usuario estándar de la aplicación"
            ),
            UserRoleModel(
                id = 2,
                name = "Guía",
                description = "Guía certificado de senderismo"
            ),
            UserRoleModel(
                id = 3,
                name = "Administrador",
                description = "Administrador del sistema"
            )
        )

        // Usuarios de muestra expandidos
        val users = listOf(
            UserModel(
                id = 1,
                username = "admin",
                email = "admin@senderismolanzarote.org",
                firstName = "Admin",
                lastName = "Sistema",
                registrationDate = LocalDateTime.now(),
                roleModel = roles[2],
                passwordHash = hashPassword("admin123")
            ),
            UserModel(
                id = 2,
                username = "guia_juan",
                email = "juan@senderismolanzarote.org",
                firstName = "Juan",
                lastName = "Pérez",
                registrationDate = LocalDateTime.now(),
                roleModel = roles[1],
                passwordHash = hashPassword("guia123")
            ),
            UserModel(
                id = 3,
                username = "maria_senderista",
                email = "maria@email.com",
                firstName = "María",
                lastName = "García",
                registrationDate = LocalDateTime.now(),
                roleModel = roles[0],
                passwordHash = hashPassword("user123")
            ),
            UserModel(
                id = 4,
                username = "carlos_guia",
                email = "carlos@senderismolanzarote.org",
                firstName = "Carlos",
                lastName = "Rodríguez",
                registrationDate = LocalDateTime.now(),
                roleModel = roles[1],
                passwordHash = hashPassword("guia456")
            ),
            UserModel(
                id = 5,
                username = "ana_montana",
                email = "ana@email.com",
                firstName = "Ana",
                lastName = "Martínez",
                registrationDate = LocalDateTime.now(),
                roleModel = roles[0],
                passwordHash = hashPassword("user456")
            ),
            UserModel(
                id = 6,
                username = "david_hiker",
                email = "david@email.com",
                firstName = "David",
                lastName = "Sánchez",
                registrationDate = LocalDateTime.now(),
                roleModel = roles[0],
                passwordHash = hashPassword("user789")
            )
        )

        // Preferencias de usuario corregidas y expandidas
        val preferences = listOf(
            UserPreferencesModel(
                id = 1,
                userModel = users[2], // María
                preferredDifficultyLevel = difficulties.find { it.id == 2 }!!, // Moderado
                maxDistanceKm = BigDecimal("10.0"),
                maxDurationMinutes = 180
            ),
            UserPreferencesModel(
                id = 2,
                userModel = users[4], // Ana
                preferredDifficultyLevel = difficulties.find { it.id == 3 }!!, // Difícil
                maxDistanceKm = BigDecimal("15.0"),
                maxDurationMinutes = 240
            ),
            UserPreferencesModel(
                id = 3,
                userModel = users[5], // David
                preferredDifficultyLevel = difficulties.find { it.id == 1 }!!, // Fácil
                maxDistanceKm = BigDecimal("8.0"),
                maxDurationMinutes = 120
            )
        )

        // Insertar datos
        roles.forEach { userRoleRepository.insertUserRole(it) }
        users.forEach { userRepository.insertUser(it) }
        preferences.forEach { userPreferencesRepository.insertUserPreferences(it) }
    }

    private fun hashPassword(password: String): String {
        return BCrypt.withDefaults().hashToString(12, password.toCharArray())
    }
}