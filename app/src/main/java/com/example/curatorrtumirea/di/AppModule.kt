package com.example.curatorrtumirea.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.curatorrtumirea.common.Constants
import com.example.curatorrtumirea.data.remote.api.AuthApi
import com.example.curatorrtumirea.data.remote.interceptor.AuthInterceptor
import com.example.curatorrtumirea.data.local.SessionManager
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
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppNavigator(appNavigatorImpl: AppNavigatorImpl): AppNavigator = appNavigatorImpl

    @EncryptedSharedPrefs
    @Provides
    @Singleton
    fun provideEncryptedSharedPrefs(
        @ApplicationContext context: Context
    ): SharedPreferences {
        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "secret_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @AuthRetrofit
    @Provides
    @Singleton
    fun provideAuthRetrofit(): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @MainRetrofit
    @Provides
    @Singleton
    fun provideMainRetrofit(
        authInterceptor: AuthInterceptor
    ): Retrofit {
        val json = Json { ignoreUnknownKeys = true }
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(json.asConverterFactory(MediaType.get("application/json")))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        @AuthRetrofit retrofit: Retrofit
    ): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventRepository(): EventRepository {
        return EventRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        api: AuthApi,
        sessionManager: SessionManager,
        @EncryptedSharedPrefs sharedPrefs: SharedPreferences
    ): AuthRepository {
        return AuthRepositoryImpl(api, sessionManager, sharedPrefs)
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