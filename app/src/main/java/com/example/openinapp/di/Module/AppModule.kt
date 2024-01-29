package com.example.openinapp.di.Module

import androidx.compose.ui.unit.Constraints
import com.example.openinapp.data.remote.OAuthInterceptor
import com.example.openinapp.data.remote.OpeninAPI
import com.example.openinapp.data.repository.DashBoardImpl
import com.example.openinapp.domain.repository.DashBoardRepository
import com.example.openinapp.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideOpeninApi(): OpeninAPI {

        val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(
                OAuthInterceptor(
                    "Bearer",
                    Constants.ACCESS_TOKEN
                )
            )
            .retryOnConnectionFailure(false)
            .build()

        return Retrofit.Builder()
            .baseUrl("https://api.inopenapp.com/api/v1/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(OpeninAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideDashBoardRepository(api: OpeninAPI): DashBoardRepository {
        return DashBoardImpl(api)
    }

}