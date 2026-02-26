package com.example.taskflow.features.task.data.repositories

import com.example.taskflow.features.task.domain.repositories.TaskRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TaskRepositoryModule {
    @Binds
    @Singleton
    abstract fun provideTaskRepository(
        impl: TaskRepositoryImpl
    ): TaskRepository
}