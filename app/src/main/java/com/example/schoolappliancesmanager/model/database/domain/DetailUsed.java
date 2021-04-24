package com.example.schoolappliancesmanager.model.database.domain;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "detail_used", primaryKeys = {"appliance_id", "room_id"})
public class DetailUsed {
    @ColumnInfo(name = "appliance_id")
    private long applianceId;
    @NonNull
    @ColumnInfo(name = "room_id")
    private String roomName;
    @ColumnInfo(name = "date_used")
    private long dateUsed;
    @ColumnInfo(name = "class_name")
    private String className;

    public long getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(long applianceId) {
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

    @NonNull
    private Calendar calendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getDefault());
        calendar.setTimeInMillis(dateUsed);
        return calendar;
    }

    public String getDateUsedString() {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar().getTime());
    }

    public void setDateUsedString(String value) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getDefault());
        try {
            dateUsed = simpleDateFormat.parse(value).getTime();
        } catch (ParseException e) {
            dateUsed = 0;
        }
    }
}