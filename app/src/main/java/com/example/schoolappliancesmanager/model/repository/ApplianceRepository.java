package com.example.schoolappliancesmanager.model.repository;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Singleton
public interface ApplianceRepository {
    Flowable<List<Appliance>> getAllData();

    Flowable<List<Appliance>> getAppliancesName(int applianceId);

    Single<List<Appliance>> getApplianceNameById(int applianceId);

    Completable insert(Appliance appliance);

    Completable delete(Appliance appliance);

    Completable update(Appliance appliance);
}
