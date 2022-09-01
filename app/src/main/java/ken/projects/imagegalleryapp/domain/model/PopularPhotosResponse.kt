package ken.projects.imagegalleryapp.domain.model

data class PopularPhotosResponse(
    val extra: Extra,
    val photos: Photos,
    val stat: String
)