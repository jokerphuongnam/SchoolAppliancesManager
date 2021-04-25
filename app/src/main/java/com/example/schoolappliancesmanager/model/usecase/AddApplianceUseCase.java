package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;

@Singleton
public interface AddApplianceUseCase {
    @NonNull Completable addAppliance(Appliance appliance);
    @NonNull Completable editAppliance(Appliance appliance);
}
