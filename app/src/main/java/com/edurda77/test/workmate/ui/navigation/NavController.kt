package com.edurda77.test.workmate.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.edurda77.test.workmate.ui.character_detail.CharacterDetailsScreenRoot
import com.edurda77.test.workmate.ui.list_characters.CharactersScreenRoot

@Composable
fun NavController(
    startDestination: NavigationRoute = NavigationRoute.CharectersRoute,
) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {

        composable<NavigationRoute.CharectersRoute> {
            CharactersScreenRoot(
                onCharacterClick = {
                    navController.navigate(NavigationRoute.CharecterDetailsRoute(it))
                }
            )
        }
        composable<NavigationRoute.CharecterDetailsRoute> {
            CharacterDetailsScreenRoot(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}