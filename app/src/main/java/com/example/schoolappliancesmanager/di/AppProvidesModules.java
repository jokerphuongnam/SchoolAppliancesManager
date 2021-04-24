package com.example.schoolappliancesmanager.di;

import android.content.Context;

import androidx.room.Room;

import com.example.schoolappliancesmanager.model.database.local.AppDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

import static com.example.schoolappliancesmanager.util.RoomUtils.DB_NAME;

@Module
@InstallIn(SingletonComponent.class)
public class AppProvidesModules {
    @Provides
    @Singleton
    AppDatabase providerDatabase(@ApplicationContext Context context){
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME ).build();
    }
}
