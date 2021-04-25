package com.example.schoolappliancesmanager.model.repository;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.local.ApplianceLocal;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultApplianceRepositoryImpl implements ApplianceRepository {
    private final ApplianceLocal local;

    @Inject
    public DefaultApplianceRepositoryImpl(ApplianceLocal local) {
        this.local = local;
    }

    @Override
    public Flowable<List<Appliance>> getAllData() {
        return local.getAllData().subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<Appliance>> getNormalData() {
        return local.getNormalData().subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insert(Appliance appliance) {
        return local.insert(appliance).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable delete(Appliance appliance) {
        return local.delete(appliance).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable update(Appliance appliance) {
        return local.update(appliance).subscribeOn(Schedulers.io());
    }
}
