package com.edurda77.test.workmate.ui.navigation
import kotlinx.serialization.Serializable

sealed class NavigationRoute {
    @Serializable
    data object CharectersRoute : NavigationRoute()

    @Serializable
    data class CharecterDetailsRoute(
        val id:Int,
    ) : NavigationRoute()
}