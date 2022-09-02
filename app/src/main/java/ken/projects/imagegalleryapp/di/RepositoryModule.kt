package ken.projects.imagegalleryapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ken.projects.imagegalleryapp.data.repository.RepositoryImpl
import ken.projects.imagegalleryapp.domain.repository.Repository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun imageRepository(imagerRepositoryImpl: RepositoryImpl): Repository

}