package ken.projects.imagegalleryapp.domain.model


data class Photo(
    val id: String,
    val owner: String,
    val secret: String,
    val server: String,
    val farm: Int,
    val title: String

)
