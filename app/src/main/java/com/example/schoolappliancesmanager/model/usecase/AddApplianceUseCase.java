package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public interface AddApplianceUseCase {
    Single<Boolean> addAppliance(Appliance appliance);
    Single<Boolean> editAppliance(Appliance appliance);
}
