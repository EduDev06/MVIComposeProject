package com.example.mvicomposeproject.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.mvicomposeproject.data.local.GenderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGenderDatabase(
        @ApplicationContext context: Context
    ): GenderDatabase = Room.databaseBuilder(
        context,
        GenderDatabase::class.java,
        "gender-database"
    ).build()
}