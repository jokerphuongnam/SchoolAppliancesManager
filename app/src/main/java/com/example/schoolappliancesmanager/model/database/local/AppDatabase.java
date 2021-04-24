package com.example.schoolappliancesmanager.model.database.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.util.RoomConverter;
import com.example.schoolappliancesmanager.util.RoomUtils;

import javax.inject.Singleton;


@Singleton
@Database(
        entities = {Appliance.class, DetailUsed.class, Room.class},
        version = RoomUtils.DB_VERSION
)
//@TypeConverters(RoomConverter.class)
public abstract class AppDatabase extends RoomDatabase {

}
