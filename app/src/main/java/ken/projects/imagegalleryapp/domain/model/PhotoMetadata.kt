package ken.projects.imagegalleryapp.domain.model

data class PhotoMetadata(
    val camera: String,
    val exif: List<Exif>,
    val farm: Int,
    val id: String,
    val secret: String,
    val server: String
)