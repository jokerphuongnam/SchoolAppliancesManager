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
@Entity(tableName = "appliances")
public class Appliance implements Serializable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "appliance_id")
    private int applianceId = -1;
    @ColumnInfo(name = "appliance_name")
    private String applianceName = "";
    @ColumnInfo(name = "dir_image")
    private String dirImage = "";
    @ColumnInfo(name = "status")
    private Status status;

    public enum Status {
        BROKEN(R.string.broken),
        NORMAL(R.string.normal),
        BORROW(R.string.borrow);

        Status(@StringRes int res) {
            this.res = res;
        }

        @StringRes
        private final int res;

        @StringRes
        public int getRes() {
            return res;
        }

        @NonNull
        public static List<String> toList(@NonNull Context context) {
            List<String> list = new ArrayList<>();
            list.add(context.getString(BROKEN.getRes()));
            list.add(context.getString(NORMAL.getRes()));
            list.add(context.getString(BORROW.getRes()));
            return list;
        }
    }

    public Appliance(String applianceName, String dirImage, Status status) {
        this.applianceName = applianceName;
        this.dirImage = dirImage;
        this.status = status;
    }

    public int getApplianceId() {
        return applianceId;
    }

    public void setApplianceId(int applianceId) {
        this.applianceId = applianceId;
    }

    public String getApplianceName() {
        return applianceName;
    }

    public void setApplianceName(String applianceName) {
        this.applianceName = applianceName;
    }

    public String getDirImage() {
        return dirImage;
    }

    public void setDirImage(String dirImage) {
        this.dirImage = dirImage;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @NonNull
    public String getStatusString(@NonNull Context context) {
        return context.getString(status.getRes());
    }
}
