package com.example.curatorrtumirea.di

import com.example.curatorrtumirea.data.repository.AttendanceRepositoryImpl
import com.example.curatorrtumirea.data.repository.AuthRepositoryImpl
import com.example.curatorrtumirea.data.repository.EventRepositoryImpl
import com.example.curatorrtumirea.data.repository.GroupRepositoryImpl
import com.example.curatorrtumirea.data.repository.StudentRepositoryImpl
import com.example.curatorrtumirea.domain.repository.AttendanceRepository
import com.example.curatorrtumirea.domain.repository.AuthRepository
import com.example.curatorrtumirea.domain.repository.EventRepository
import com.example.curatorrtumirea.domain.repository.GroupRepository
import com.example.curatorrtumirea.domain.repository.StudentRepository
import com.example.curatorrtumirea.presentation.navigation.AppNavigator
import com.example.curatorrtumirea.presentation.navigation.AppNavigatorImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator = appNavigatorImpl

    @Provides
    @Singleton
    fun provideEventRepository(): EventRepository {
        return EventRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository {
        return AuthRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideGroupRepository(): GroupRepository {
        return GroupRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAttendanceRepository(): AttendanceRepository {
        return AttendanceRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideStudentRepository(): StudentRepository {
        return StudentRepositoryImpl()
    }
}