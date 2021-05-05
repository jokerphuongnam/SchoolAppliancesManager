package com.example.schoolappliancesmanager.model.database.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;

import com.example.schoolappliancesmanager.util.DateUtil;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static androidx.room.ForeignKey.CASCADE;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(
        tableName = "detail_used",
        primaryKeys = {"appliance_id", "room_id", "date_used"},
        foreignKeys = {
                @ForeignKey(
                        entity = Appliance.class,
                        parentColumns = "appliance_id",
                        childColumns = "appliance_id",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                ),

                @ForeignKey(
                        entity = Room.class,
                        parentColumns = "room_id",
                        childColumns = "room_id",
                        onDelete = CASCADE,
                        onUpdate = CASCADE
                )
        }
)
public class DetailUsed implements Serializable {
    @ColumnInfo(name = "appliance_id")
    private int applianceId = -1;
    @NonNull
    @ColumnInfo(name = "room_id")
    private String roomName;
    @ColumnInfo(name = "date_used")
    private long dateUsed;
    @ColumnInfo(name = "class_name")
    private String className = "";
    @ColumnInfo(name = "returned_time")
    private long returnTime = 0;

    public int getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(int applianceId) {
        this.applianceId = applianceId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public long getDateUsed() {
        return dateUsed;
    }

    public void setDateUsed(long dateUsed) {
        this.dateUsed = dateUsed;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public long getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(long returnTime) {
        this.returnTime = returnTime;
    }

    @NonNull
    private Calendar calendarBorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        if (dateUsed != 0) {
            calendar.setTimeInMillis(dateUsed);
        }
        return calendar;
    }

    @NonNull
    private Calendar calendarReturned() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        if (returnTime != 0) {
            calendar.setTimeInMillis(returnTime);
        }
        return calendar;
    }

    public String getDateUsedString() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendarBorrow().getTime());
    }

    public void setDateUsedString(String value) {
        dateUsed = DateUtil.stringDateToLong(value);
    }

    public String getReturnTimeString() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendarReturned().getTime());
    }
}
