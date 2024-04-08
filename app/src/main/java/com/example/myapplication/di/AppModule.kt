package com.example.myapplication.di

import android.content.Context
import com.example.myapplication.domain.api.IListingsApi
import com.example.myapplication.domain.api.IListingsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    fun provideListingsRepository(@ApplicationContext context: Context): IListingsApi {
        return IListingsRepositoryImpl(context)
    }
}