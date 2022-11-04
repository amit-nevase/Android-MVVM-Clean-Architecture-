package com.amit.assignment.di

import android.app.Application
import androidx.room.Room
import com.amit.assignment.data.source.local.AppDatabase
import com.amit.assignment.data.source.local.dao.CharacterDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            AppDatabase.DB_NAME
        ).allowMainThreadQueries().build()
    }

    @Provides
    internal fun provideCharacterDao(appDatabase: AppDatabase): CharacterDao {
        return appDatabase.characterDao
    }
}
