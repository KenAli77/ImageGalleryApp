package ken.projects.imagegalleryapp.data.remote

import ken.projects.imagegalleryapp.BuildConfig
import ken.projects.imagegalleryapp.domain.model.MetadataResponse
import ken.projects.imagegalleryapp.domain.model.PopularPhotosResponse
import ken.projects.imagegalleryapp.domain.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesAPI {

    companion object {
        const val API_KEY = BuildConfig.API_KEY

    }

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&sort=interestingness-desc&api_key=$API_KEY")
    suspend fun searchImages(
        @Query("text") query: String? = null,
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = 100,
    ): SearchResponse

    @GET("?method=flickr.interestingness.getList&format=json&nojsoncallback=1&api_key=$API_KEY")
    suspend fun fetchPopularImages(
        @Query("page") page: Int? = null,
        @Query("per_page") perPage: Int? = 100,
    ): PopularPhotosResponse

    @GET("?method=flickr.photos.getExif&format=json&nojsoncallback=1&api_key=$API_KEY")
    suspend fun getPhotoMetadata(
        @Query("photo_id") photoId:String
    ):MetadataResponse

}
