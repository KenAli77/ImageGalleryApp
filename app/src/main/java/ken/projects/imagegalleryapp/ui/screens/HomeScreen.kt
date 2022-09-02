package ken.projects.imagegalleryapp.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.skydoves.landscapist.glide.GlideImage
import ken.projects.imagegalleryapp.R
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.ui.viewmodel.ImageViewModel
import ken.projects.imagegalleryapp.ui.navigation.Screens
import ken.projects.imagegalleryapp.ui.navigation.TopBar
import ken.projects.imagegalleryapp.ui.theme.Purple
import ken.projects.imagegalleryapp.ui.theme.outfit

@Composable
fun HomeScreen(
    popularImages: LazyPagingItems<PhotoItem>,
    filter1: LazyPagingItems<PhotoItem>,
    filter2: LazyPagingItems<PhotoItem>,
    viewModel: ImageViewModel,
    navHostController: NavHostController,
) = with(viewModel) {

    val scrollState = rememberScrollState()

    Scaffold(
        topBar = { TopBar(title = stringResource(R.string.appbar_text)) },
        modifier = Modifier
            .fillMaxSize(),
    ) {


        Column(
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(50.dp),
            modifier = Modifier
                .padding(top = it.calculateTopPadding())
                .verticalScroll(scrollState)
        ) {

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = stringResource(R.string.kittens),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = outfit,
                ),
                modifier = Modifier.padding(start = 10.dp)
            )

            if (filter2.itemSnapshotList.isNotEmpty()) {
                LazyRow {

                    items(filter2) { image ->
                        ImageItem(image!!) {
                            setImageDetail(image)
                            navHostController.navigate(Screens.Details.route)
                        }
                    }
                }
            } else {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = Purple,
                        strokeWidth = 5.dp,
                    )
                }
            }

            Text(
                text = stringResource(R.string.dogs),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = outfit
                ),
                modifier = Modifier.padding(start = 10.dp)
            )

            if (filter1.itemSnapshotList.isNotEmpty()) {
                LazyRow {
                    items(filter1) { image ->
                        ImageItem(image!!) {
                            setImageDetail(image)
                            navHostController.navigate(Screens.Details.route)
                        }
                    }
                }
            } else {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = Purple,
                        strokeWidth = 5.dp,
                    )
                }
            }

            Text(
                text = stringResource(R.string.trending),
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = outfit
                ),
                modifier = Modifier.padding(start = 10.dp)
            )



            if (popularImages.itemSnapshotList.isNotEmpty()) {
                LazyRow {
                    items(popularImages) { image ->
                        ImageItem(image!!) {
                            setImageDetail(image)
                            navHostController.navigate(Screens.Details.route)
                        }
                    }
                }
            } else {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CircularProgressIndicator(
                        color = Purple,
                        strokeWidth = 5.dp,
                    )
                }
            }

            Spacer(modifier = Modifier.height(65.dp))

        }

    }
}

@Composable
fun ImageItem(data: PhotoItem, onClick: () -> Unit) {
    Surface(
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .size(150.dp)
            .padding(horizontal = 5.dp),
        elevation = 10.dp
    ) {

        Column(modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }) {

            GlideImage(
                imageModel = data.url,
                contentScale = ContentScale.Crop,
                placeHolder = Icons.Rounded.Image,

                )
        }


    }
}



