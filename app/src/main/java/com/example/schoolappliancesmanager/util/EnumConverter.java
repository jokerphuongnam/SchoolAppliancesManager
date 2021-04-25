package com.example.schoolappliancesmanager.util;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.domain.Room;

public class EnumConverter {
    @TypeConverter
    public int roomTypeToInt(@NonNull Room.RoomType roomType){
        return roomType.ordinal();
    }

    @TypeConverter
    public  Room.RoomType intToRoomType(int index){
        return Room.RoomType.values()[index];
    }

    @TypeConverter
    public int statusToInt(@NonNull Appliance.Status status){
        return status.ordinal();
    }

    @TypeConverter
    public  Appliance.Status inToStatus(int index){
        return Appliance.Status.values()[index];
    }
}
