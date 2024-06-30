package com.example.connectapp.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.connectapp.Data.database.ContactDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {

    @Provides
    
    fun providerDatabase(application: Application): ContactDatabase{

        return Room.databaseBuilder(application.applicationContext,
            ContactDatabase::class.java,
            "ConnectApp.db")
            .setJournalMode(RoomDatabase.JournalMode.TRUNCATE)
            .fallbackToDestructiveMigration().build()
    }
}