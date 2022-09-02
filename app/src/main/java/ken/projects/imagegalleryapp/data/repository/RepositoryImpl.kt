package ken.projects.imagegalleryapp.data.repository

import ken.projects.imagegalleryapp.data.local.ImageDatabase
import ken.projects.imagegalleryapp.data.remote.ImagesAPI
import ken.projects.imagegalleryapp.domain.model.MetadataResponse
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.domain.model.PopularPhotosResponse
import ken.projects.imagegalleryapp.domain.repository.Repository
import ken.projects.imagegalleryapp.domain.model.SearchResponse
import ken.projects.imagegalleryapp.util.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ImagesAPI,
    private val db: ImageDatabase
) : Repository {

    override suspend fun searchPhotos(
        query: String,
        page: Int?,
        perPage: Int?
    ): Resource<SearchResponse> {

        return try {

            val data = api.searchImages(query, page = page, perPage = perPage)
            Resource.Success(data)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message ?: "An unknown error has occurred")
        }
    }

    override suspend fun getPhotoMetaData(photoId: String): Resource<MetadataResponse> {
        return try {

            val data = api.getPhotoMetadata(photoId)
            Resource.Success(data)

        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override suspend fun getPopularPhotos(
        page: Int?,
        perPage: Int?
    ): Resource<PopularPhotosResponse> {
        return try {
            val data = api.fetchPopularImages(page = page, perPage = perPage)
            Resource.Success(data)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.message.toString())
        }
    }

    override fun getFavoriteImages(): Flow<List<PhotoItem>> {
        return db.imageDao.getImages()
    }

    override suspend fun addImageToFavorites(image: PhotoItem) {
        return db.imageDao.insertImage(image)
    }

    override suspend fun removeImageFromFavorites(image: PhotoItem) {
        return db.imageDao.removeImage(image)
    }
}