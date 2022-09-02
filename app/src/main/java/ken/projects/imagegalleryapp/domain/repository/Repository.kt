package ken.projects.imagegalleryapp.domain.repository

import ken.projects.imagegalleryapp.domain.model.MetadataResponse
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.domain.model.PopularPhotosResponse
import ken.projects.imagegalleryapp.domain.model.SearchResponse
import ken.projects.imagegalleryapp.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun searchPhotos(query:String,page:Int?=null,perPage:Int?=null): Resource<SearchResponse>
    suspend fun getPopularPhotos(page:Int?=null, perPage:Int?=null): Resource<PopularPhotosResponse>
    suspend fun getPhotoMetaData(photoId:String):Resource<MetadataResponse>

    fun getFavoriteImages(): Flow<List<PhotoItem>>
    suspend fun addImageToFavorites(image:PhotoItem)
    suspend fun removeImageFromFavorites(image:PhotoItem)
}