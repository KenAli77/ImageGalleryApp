package ken.projects.imagegalleryapp.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import ken.projects.imagegalleryapp.data.paging.PopularImagesDataSource
import ken.projects.imagegalleryapp.data.paging.SearchImageDataSource
import ken.projects.imagegalleryapp.domain.model.ImageState
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.domain.repository.Repository
import ken.projects.imagegalleryapp.util.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageViewModel @Inject constructor(
    private val repo: Repository
) : ViewModel() {

    var queryImageState by mutableStateOf(ImageState())
        private set

    var favoriteImagesState by mutableStateOf(ImageState())

    var imageDetail by mutableStateOf(ImageState())
        private set

    var metadataState by mutableStateOf(ImageState())
        private set


    val popularImagesPager = Pager(
        PagingConfig(pageSize = 100)
    ) {
        PopularImagesDataSource(repo)
    }.flow.cachedIn(viewModelScope)

    val searchImagePager1 = Pager(
        PagingConfig(pageSize = 100)
    ) {
        SearchImageDataSource(repo, "dogs")
    }.flow.cachedIn(viewModelScope)

    val searchImagePager2 = Pager(
        PagingConfig(pageSize = 100)
    ) {
        SearchImageDataSource(repo, "kittens")
    }.flow.cachedIn(viewModelScope)

    fun setImageDetail(image: PhotoItem) {

        imageDetail = imageDetail.copy(image = image)
        getPhotoMetadata(image.id)
    }

    fun searchImages(query: String) = viewModelScope.launch {

        queryImageState = queryImageState.copy(
            loading = true,
        )

        when (val searchResponse = repo.searchPhotos(query)) {
            is Resource.Error -> {

                queryImageState = queryImageState.copy(
                    error = searchResponse.message
                )

            }
            is Resource.Success -> {
                val images = ArrayList<PhotoItem>()
                searchResponse.data?.let {
                    Log.e("resp", it.photos.page.toString())
                    it.photos.photo.forEach { image ->
                        Log.e("image", image.id)
                        val imageObject = PhotoItem(
                            id = image.id,
                            url = "https://farm${image.farm}.staticflickr.com/${image.server}/${image.id}_${image.secret}.jpg",
                            title = image.title
                        )
                        images.add(imageObject)
                    }
                }
                queryImageState = queryImageState.copy(
                    images = images,
                    loading = false,
                )


            }


        }
    }

    private fun getPhotoMetadata(photoId: String) = viewModelScope.launch {

        metadataState = metadataState.copy(
            loading = true,
        )

        when (val result = repo.getPhotoMetaData(photoId)) {

            is Resource.Error -> {

                metadataState = metadataState.copy(
                    error = result.message.toString(),
                )
            }
            is Resource.Success -> {

                metadataState = metadataState.copy(
                    metadata = result.data?.photo,
                    loading = false
                )
            }

        }

    }


    init {
        getFavorites()
    }


    private fun getFavorites() = viewModelScope.launch {

        repo.getFavoriteImages().collect {

            favoriteImagesState = favoriteImagesState.copy(images = it)

        }

    }

    fun addImageToFavorites(image: PhotoItem) = viewModelScope.launch {

        repo.addImageToFavorites(image)

    }

    fun removeImageFromFavorites(image: PhotoItem) = viewModelScope.launch {

        repo.removeImageFromFavorites(image)

    }


}


