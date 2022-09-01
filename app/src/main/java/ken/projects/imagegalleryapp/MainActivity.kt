package ken.projects.imagegalleryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import dagger.hilt.android.AndroidEntryPoint
import ken.projects.imagegalleryapp.ui.viewmodel.ImageViewModel
import ken.projects.imagegalleryapp.ui.navigation.SetUpNavGraph
import ken.projects.imagegalleryapp.ui.theme.ImageGalleyAppTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ImageViewModel by viewModels()
    lateinit var navHostController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        setContent {

            ImageGalleyAppTheme {
                navHostController = rememberNavController()

                val popularPhotos = viewModel.popularImagesPager.collectAsLazyPagingItems()
                val dogPhotos = viewModel.searchImagePager1.collectAsLazyPagingItems()
                val kittenPhotos = viewModel.searchImagePager2.collectAsLazyPagingItems()


                SetUpNavGraph(navHostController,popularPhotos,dogPhotos,kittenPhotos,viewModel)


            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ImageGalleyAppTheme {

    }
}