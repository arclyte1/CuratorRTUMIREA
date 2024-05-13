package com.example.curatorrtumirea.di

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.curatorrtumirea.common.Constants
import com.example.curatorrtumirea.data.remote.api.AuthApi
import com.example.curatorrtumirea.data.remote.interceptor.AuthInterceptor
import com.example.curatorrtumirea.data.local.SessionManager
import com.example.curatorrtumirea.data.remote.api.MainApi
import com.example.curatorrtumirea.data.repository.AuthRepositoryImpl
import com.example.curatorrtumirea.data.repository.EventRepositoryImpl
import com.example.curatorrtumirea.data.repository.GroupRepositoryImpl
import com.example.curatorrtumirea.data.repository.RequestRepositoryImpl
import com.example.curatorrtumirea.domain.repository.AuthRepository
import com.example.curatorrtumirea.domain.repository.EventRepository
import com.example.curatorrtumirea.domain.repository.GroupRepository
import com.example.curatorrtumirea.domain.repository.RequestRepository
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
            .build()
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
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
    fun provideMainApi(
        @MainRetrofit retrofit: Retrofit
    ): MainApi {
        return retrofit.create(MainApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventRepository(
        api: MainApi
    ): EventRepository {
        return EventRepositoryImpl(api)
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
    fun provideGroupRepository(
        api: MainApi
    ): GroupRepository {
        return GroupRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideRequestRepository(
        api: MainApi
    ): RequestRepository {
        return RequestRepositoryImpl(api)
    }
}