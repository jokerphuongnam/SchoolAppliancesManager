package com.example.schoolappliancesmanager.model.database.domain;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.schoolappliancesmanager.R;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(tableName = "rooms")
public class Room implements Serializable {
    @PrimaryKey
    @ColumnInfo(name = "room_id")
    @NonNull
    private String roomName = "";
    @ColumnInfo(name = "type")
    private RoomType type;

    public enum RoomType {
        STUDY(R.string.study),
        LAB(R.string.lab);

        RoomType(@StringRes int res) {
            this.res = res;
        }

        @StringRes
        private final int res;

        @StringRes
        public int getRes() {
            return res;
        }

        public static List<String> toList(Context context) {
            List<String> list = new ArrayList<>();
            list.add(context.getString(STUDY.getRes()));
            list.add(context.getString(LAB.getRes()));
            return list;
        }
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    @NonNull
    public String getTypeString(@NonNull Context context) {
        return context.getString(type.getRes());
    }
}
