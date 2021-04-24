package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class DefaultAddApplianceUseCaseImpl implements AddApplianceUseCase {

    @Inject
    public DefaultAddApplianceUseCaseImpl(){}

    @Override
    public Single<Boolean> addAppliance(Appliance appliance) {
        return  Single.just(true);
    }

    @Override
    public Single<Boolean> editAppliance(Appliance appliance) {
        return  Single.just(true);
    }
}
