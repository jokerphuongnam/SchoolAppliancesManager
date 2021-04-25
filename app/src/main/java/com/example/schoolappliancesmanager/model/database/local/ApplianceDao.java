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
    default Flowable<List<Appliance>> getNormalData() {
        return getNormalData(Appliance.Status.NORMAL.ordinal());
    }

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
