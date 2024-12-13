package org.iesharia.senderismolanzarote.domain.model.user

import java.time.LocalDateTime

data class FavoriteRoute(
    val userId: Int,
    val routeId: Int,
    val addedDate: LocalDateTime = LocalDateTime.now()
)