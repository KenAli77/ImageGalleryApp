package ken.projects.imagegalleryapp.domain.model

data class Exif(
    val clean: Clean,
    val label: String, // thi
    val raw: Raw, // thi
    val tag: String,
    val tagspace: String,
    val tagspaceid: Int
)