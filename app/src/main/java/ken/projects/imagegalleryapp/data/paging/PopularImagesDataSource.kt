package ken.projects.imagegalleryapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import ken.projects.imagegalleryapp.domain.repository.Repository


class PopularImagesDataSource(private val repo: Repository) :
    PagingSource<Int, PhotoItem>() {

    override fun getRefreshKey(state: PagingState<Int, PhotoItem>): Int? {
        return state.anchorPosition?.let { position ->
            val page = state.closestPageToPosition(position)
            page?.prevKey?.minus(1) ?: page?.nextKey?.plus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PhotoItem> {
        return try {

            val page = params.key ?: 1
            val response = repo.getPopularPhotos(page = page, perPage = 100)
            val photos = ArrayList<PhotoItem>()

            response.data?.photos?.photo?.forEach { image ->
                val photoItem = PhotoItem(
                    id = image.id,
                    url = "https://farm${image.farm}.staticflickr.com/${image.server}/${image.id}_${image.secret}.jpg",
                    title = image.title
                )

                photos.add(photoItem)
            }

            LoadResult.Page(
                data = photos,
                prevKey = null,
                nextKey = if (response.data!!.photos.pages > page) response.data.photos.page + 1 else null
            )


        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}