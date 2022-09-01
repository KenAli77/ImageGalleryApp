package ken.projects.imagegalleryapp.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.ui.navigation.NoInternetView
import ken.projects.imagegalleryapp.ui.viewmodel.ImageViewModel
import ken.projects.imagegalleryapp.ui.navigation.Screens
import ken.projects.imagegalleryapp.ui.navigation.TopBar
import ken.projects.imagegalleryapp.ui.theme.Cyan
import kotlinx.coroutines.launch

@Composable
fun FavoriteScreen(
    viewModel: ImageViewModel,
    navHostController: NavHostController,
    scaffoldState: ScaffoldState,
    isConnected: Boolean
) =
    with(viewModel) {

        var selectedImage by remember { mutableStateOf(PhotoItem(id = "", url = "", title = "")) }

        val scope = rememberCoroutineScope()

        var openDialog by remember { mutableStateOf(false) }

        if (openDialog) {
            AlertDialog(
                onDismissRequest = {

                    openDialog = false
                },
                title = {
                    Text(text = "Remove photo")
                },
                text = {
                    Text(
                        "are you sure you want to remove this image from your favorites?"
                    )
                },
                confirmButton = {
                    TextButton(
                        onClick = {
                            if (selectedImage.id.isNotEmpty())
                                removeImageFromFavorites(selectedImage)
                            scope.launch {
                                scaffoldState.snackbarHostState.showSnackbar(
                                    "image removed",
                                    "UNDO",
                                    SnackbarDuration.Long
                                ).also {
                                    if (it == SnackbarResult.ActionPerformed) {
                                        addImageToFavorites(selectedImage)
                                    }
                                }


                            }
                            openDialog = false


                        }
                    ) {
                        Text("confirm", color = Color.Red)
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            openDialog = false
                        }
                    ) {
                        Text("dismiss", color = Cyan)
                    }
                },

                )
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopBar(
                    title = "Favorites",
                    onBackPressed = {}
                )
            }) {

            if (isConnected)
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {

                    favoriteImagesState.images?.let {

                        StaggeredGridView(images = it, onImageClick = { photo ->
                            setImageDetail(photo)
                            navHostController.navigate(Screens.Details.route)
                        }, onHoldClick = { photo ->
                            selectedImage = photo
                            openDialog = true

                        }
                        )


                    } ?: Text(
                        text = "nothing to see here",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                }
            else NoInternetView()
        }

    }