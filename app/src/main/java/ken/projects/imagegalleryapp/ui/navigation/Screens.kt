package ken.projects.imagegalleryapp.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String, val icon: ImageVector? = null
) {

    object Home : Screens(route = "home_screen", title = "Home", icon = Icons.Rounded.Home)
    object Favorites : Screens(route = "favorites_screen", title = "Favorites",icon = Icons.Rounded.Favorite)
    object Search : Screens(route = "search_screen", title = "Search",icon = Icons.Rounded.Search)
    object Details : Screens(route = "detail_screen", title = "Details")
}