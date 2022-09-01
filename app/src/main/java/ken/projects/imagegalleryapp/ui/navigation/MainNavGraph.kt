package ken.projects.imagegalleryapp.ui.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.LazyPagingItems
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.ui.viewmodel.ImageViewModel
import ken.projects.imagegalleryapp.ui.screens.DetailScreen
import ken.projects.imagegalleryapp.ui.screens.FavoriteScreen
import ken.projects.imagegalleryapp.ui.screens.HomeScreen
import ken.projects.imagegalleryapp.ui.screens.SearchScreen
import ken.projects.imagegalleryapp.ui.theme.Cyan
import ken.projects.imagegalleryapp.ui.theme.outfit
import ken.projects.imagegalleryapp.util.ConnectionState
import ken.projects.imagegalleryapp.util.connectivityState

@Composable
fun SetUpNavGraph(
    navHostController: NavHostController,
    popularImages: LazyPagingItems<PhotoItem>,
    filter1: LazyPagingItems<PhotoItem>,
    filter2: LazyPagingItems<PhotoItem>,
    viewModel: ImageViewModel
) {

    val scaffoldState = rememberScaffoldState()

    val connection by connectivityState()

    val isConnected = connection === ConnectionState.Available

    Scaffold(bottomBar = { BottomNavBar(navHostController) }, scaffoldState = scaffoldState) { _ ->

        NavHost(
            navController = navHostController,
            startDestination = Screens.Home.route,
            modifier = Modifier.padding(),
        ) {
            composable(route = Screens.Home.route) {
                HomeScreen(
                    popularImages = popularImages,
                    filter1 = filter1,
                    filter2 = filter2,
                    viewModel = viewModel,
                    navHostController = navHostController,
                    isConnected = isConnected
                )
            }
            composable(route = Screens.Favorites.route) {
                FavoriteScreen(
                    viewModel, navHostController, scaffoldState, isConnected = isConnected
                )
            }
            composable(route = Screens.Search.route) {
                SearchScreen(
                    viewModel, navHostController, isConnected = isConnected
                )
            }
            composable(route = Screens.Details.route) {
                DetailScreen(
                    navHostController, viewModel, isConnected = isConnected
                )
            }


        }
    }

}


@Composable
fun BottomNavBar(
    navController: NavHostController = rememberNavController(),
) {
    val screens = listOf(
        Screens.Home,
        Screens.Favorites,
        Screens.Search,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation(
        modifier = Modifier
            .height(70.dp)
            .graphicsLayer {
                clip = true
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            },
        elevation = 10.dp,
        backgroundColor = Cyan,

        ) {

        screens.forEach {

            this@BottomNavigation.AddItem(
                screens = it,
                currentDestination = currentDestination,
                navController = navController
            )
        }

    }

}

@Composable
fun RowScope.AddItem(
    screens: Screens,
    currentDestination: NavDestination?,
    navController: NavHostController
) {

    BottomNavigationItem(
        label = {
            Text(text = screens.title)
        },

        onClick = {
            navController.navigate(screens.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        },
        icon = { Icon(imageVector = screens.icon!!, contentDescription = null) },
        selected = currentDestination?.hierarchy?.any { it.route == screens.route } == true,
        selectedContentColor = Color.White,
        unselectedContentColor = Color.White.copy(ContentAlpha.disabled),


        )
}


@Composable
fun TopBar(title: String, onBackPressed: () -> Unit, backNavEnabled: Boolean = false) {

    TopAppBar(
        title = {
            Text(text = title, style = TextStyle(fontFamily = outfit, fontSize = 25.sp))
        },
        navigationIcon = if (backNavEnabled) {
            {
                IconButton(onClick = { onBackPressed() }) {
                    Icon(Icons.Rounded.ArrowBack, "backIcon")
                }
            }
        } else null,
        backgroundColor = Cyan,
        contentColor = Color.White,
        elevation = 10.dp
    )
}

@Composable
fun NoInternetView() {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(text = "you are offline")
        }
    }
}
