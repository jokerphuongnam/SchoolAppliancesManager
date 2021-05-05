package com.example.schoolappliancesmanager.model.database.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface ApplianceDao extends ApplianceLocal {
    @Query("SELECT * FROM APPLIANCES")
    @Override
    Flowable<List<Appliance>> getAllData();

    @Query("SELECT * FROM APPLIANCES")
    @Override
    List<Appliance> getAll();

    @Query("SELECT * FROM APPLIANCES WHERE status = :normal")
    Flowable<List<Appliance>> getNormalData(int normal);

    @Override
    default Flowable<List<Appliance>> getAppliancesName(int applianceId) {
        if(applianceId == -1){
            return getNormalData(Appliance.Status.NORMAL.ordinal());
        }
        return getApplianceNameById(applianceId);
    }

    @Query("SELECT * FROM APPLIANCES WHERE appliance_id = :applianceId")
    Flowable<List<Appliance>> getApplianceNameById(int applianceId);

    @Insert
    @Override
    Completable insert(Appliance appliance);

    @Delete
    @Override
    Completable delete(Appliance appliance);

    @Update
    @Override
    Completable update(Appliance appliance);
}
