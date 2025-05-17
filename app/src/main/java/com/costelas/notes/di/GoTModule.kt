package com.costelas.notes.di

import GoTRepositoryImpl
import com.costelas.notes.domain.IGoTApiService
import com.costelas.notes.domain.IGoTRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GoTModule {

    @Provides
    @Singleton
    fun provideGoTRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.gameofthronesquotes.xyz/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGoTApi(retrofit: Retrofit): IGoTApiService {
        return retrofit.create(IGoTApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideGoTRepository(api: IGoTApiService): IGoTRepository {
        return GoTRepositoryImpl(api)
    }
}