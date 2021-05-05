package com.example.schoolappliancesmanager.model.database.local;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface ApplianceLocal {
    Flowable<List<Appliance>> getAllData();

    /**
     * for test data
     * */
    List<Appliance> getAll();

    Flowable<List<Appliance>> getAppliancesName(int applianceId);

    Single<List<Appliance>> getApplianceNameById(int applianceId);

    Completable insert(Appliance appliance);

    Completable delete(Appliance appliance);

    Completable update(Appliance appliance);
}
