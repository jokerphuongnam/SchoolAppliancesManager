package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.repository.ApplianceRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultAddApplianceUseCaseImpl implements AddApplianceUseCase {

    private final ApplianceRepository repository;

    @Inject
    public DefaultAddApplianceUseCaseImpl(ApplianceRepository repository) {
        this.repository = repository;
    }

    @Override
    public @NonNull Completable addAppliance(Appliance appliance) {
        return  repository.insert(appliance).subscribeOn(Schedulers.io());
    }

    @Override
    public @NonNull Completable editAppliance(Appliance appliance) {
        return  repository.update(appliance).subscribeOn(Schedulers.io());
    }
}
