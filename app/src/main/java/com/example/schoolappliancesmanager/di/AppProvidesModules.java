package com.example.schoolappliancesmanager.di;

import android.content.Context;

import androidx.room.Room;

import com.example.schoolappliancesmanager.model.database.local.AppDatabase;
import com.example.schoolappliancesmanager.model.database.local.ApplianceLocal;
import com.example.schoolappliancesmanager.model.database.local.DetailUsedLocal;
import com.example.schoolappliancesmanager.model.database.local.RoomLocal;

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
    AppDatabase providerDatabase(@ApplicationContext Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, DB_NAME).build();
    }

    @Provides
    @Singleton
    ApplianceLocal providerApplianceLocal(AppDatabase appDatabase) {
        return appDatabase.getApplianceDao();
    }

    @Provides
    @Singleton
    DetailUsedLocal providerDetailUsedLocal(AppDatabase appDatabase) {
        return appDatabase.getDetailUsedDao();
    }

    @Provides
    @Singleton
    RoomLocal providerRoomLocal(AppDatabase appDatabase) {
        return appDatabase.getRoomDao();
    }
}
