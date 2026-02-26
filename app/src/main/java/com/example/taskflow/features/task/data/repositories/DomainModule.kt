package com.example.taskflow.features.task.data.repositories

import com.example.taskflow.features.task.domain.repositories.TaskRepository
import com.example.taskflow.features.task.domain.usecases.DeleteTaskUseCase
import com.example.taskflow.features.task.domain.usecases.PostTaskUseCase
import com.example.taskflow.features.task.domain.usecases.PutTaskUseCase
import com.example.taskflow.features.task.domain.usecases.WatchTasksUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DomainModule {
    @Provides
    @Singleton
    fun providePostTaskUseCase(repository: TaskRepository): PostTaskUseCase {
        return PostTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideWatchPostsUseCase(repository: TaskRepository) : WatchTasksUseCase {
        return WatchTasksUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeletePostUseCase(repository: TaskRepository): DeleteTaskUseCase {
        return DeleteTaskUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUpdateTaskUseCase(repository: TaskRepository) : PutTaskUseCase {
        return PutTaskUseCase(repository)
    }
}