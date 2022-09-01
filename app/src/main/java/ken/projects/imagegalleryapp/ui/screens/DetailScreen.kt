package ken.projects.imagegalleryapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.skydoves.landscapist.glide.GlideImage
import ken.projects.imagegalleryapp.domain.model.ImageState
import ken.projects.imagegalleryapp.ui.viewmodel.ImageViewModel
import ken.projects.imagegalleryapp.ui.navigation.TopBar
import ken.projects.imagegalleryapp.ui.theme.outfit

@Composable
fun DetailScreen(
    imageState: ImageState,
    navHostController: NavHostController,
    viewModel: ImageViewModel
) = with(viewModel) {


    val uriHandler = LocalUriHandler.current

    imageState.image?.let { photoItem ->




        Scaffold(
            topBar = {
                TopBar(
                    title = "Image Detail",
                    onBackPressed = { navHostController.navigateUp() },
                    backNavEnabled = true
                )
            }
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround,
            ) {

                Text(
                    text = photoItem.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 25.sp,
                        fontFamily = outfit
                    ),
                    modifier = Modifier.padding(start = 10.dp)
                )

                GlideImage(
                    imageModel = photoItem.url,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(450.dp)
                        .clickable {
                            uriHandler.openUri(photoItem.url)
                        }
                )

                val context = LocalContext.current
                IconButton(onClick = {
                    if(favoriteImagesState.images!!.contains(photoItem)){
                        removeImageFromFavorites(photoItem)
                    } else {
                        addImageToFavorites(photoItem)
                        Toast.makeText(context, "added to favorites", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    if(favoriteImagesState.images!!.contains(photoItem))
                    Icon(
                        imageVector = Icons.Rounded.Favorite,
                        contentDescription = "add to favorites",
                        modifier = Modifier.size(50.dp),
                        tint = Color.Red
                    ) else {
                        Icon(
                            imageVector = Icons.Rounded.FavoriteBorder,
                            contentDescription = "add to favorites",
                            modifier = Modifier.size(50.dp),
                            tint = Color.Red
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(65.dp))

            }

        }

    }
}