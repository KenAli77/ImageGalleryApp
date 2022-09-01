package ken.projects.imagegalleryapp.data.local

import androidx.room.*
import ken.projects.imagegalleryapp.domain.model.PhotoItem
import kotlinx.coroutines.flow.Flow

@Dao
interface ImageDao {

    @Query("SELECT * FROM photos")
    fun getImages(): Flow<List<PhotoItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertImage(image: PhotoItem)

    @Delete
    suspend fun removeImage(image: PhotoItem)

}
