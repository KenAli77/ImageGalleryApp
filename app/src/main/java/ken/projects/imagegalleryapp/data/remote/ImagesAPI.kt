package ken.projects.imagegalleryapp.data.remote

import ken.projects.imagegalleryapp.domain.model.PopularPhotosResponse
import ken.projects.imagegalleryapp.domain.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImagesAPI {

    @GET("?method=flickr.photos.search&format=json&nojsoncallback=1&sort=interestingness-desc&api_key=6f4a1d91d13d45b09898828a9b8136b7")
    suspend fun searchImages(
        @Query("text") query:String? = null,
        @Query("page") page:Int? = null,
        @Query("per_page") perPage:Int? = 100,
    ): SearchResponse

    @GET("?method=flickr.interestingness.getList&format=json&nojsoncallback=1&api_key=6f4a1d91d13d45b09898828a9b8136b7")
    suspend fun fetchPopularImages(
        @Query("page") page:Int? = null,
        @Query("per_page") perPage:Int? = 100,
    ): PopularPhotosResponse

}
