package ken.projects.imagegalleryapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import ken.projects.imagegalleryapp.domain.model.PhotoItem

@Database(

    entities = [PhotoItem::class],
    version = 1,
    exportSchema = false
)
abstract class ImageDatabase:RoomDatabase() {

    abstract val imageDao:ImageDao

    companion object{

        const val DATABASE_NAME = "image_db"

    }

}