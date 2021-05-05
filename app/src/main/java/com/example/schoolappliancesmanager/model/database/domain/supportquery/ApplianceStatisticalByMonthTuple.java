package com.example.schoolappliancesmanager.model.database.domain.supportquery;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApplianceStatisticalByMonthTuple implements Parcelable {
    @ColumnInfo(name = "appliance_id")
    private int applianceId = 0;
    @ColumnInfo(name = "appliance_name")
    private String applianceName = null;
    @ColumnInfo(name = "dir_image")
    private String dirImage = null;
    @ColumnInfo(name = "quantity")
    private int quantity = 0;

    protected ApplianceStatisticalByMonthTuple(Parcel in) {
        applianceId = in.readInt();
        applianceName = in.readString();
        dirImage = in.readString();
        quantity = in.readInt();
    }

    public static final Creator<ApplianceStatisticalByMonthTuple> CREATOR = new Creator<ApplianceStatisticalByMonthTuple>() {
        @Override
        public ApplianceStatisticalByMonthTuple createFromParcel(Parcel in) {
            return new ApplianceStatisticalByMonthTuple(in);
        }

        @Override
        public ApplianceStatisticalByMonthTuple[] newArray(int size) {
            return new ApplianceStatisticalByMonthTuple[size];
        }
    };

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(applianceId);
        dest.writeString(applianceName);
        dest.writeString(dirImage);
        dest.writeInt(quantity);
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
