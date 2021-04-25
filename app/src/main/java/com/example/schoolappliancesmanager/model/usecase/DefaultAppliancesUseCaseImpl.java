package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.repository.ApplianceRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultAppliancesUseCaseImpl implements AppliancesUseCase {

    private final ApplianceRepository repository;

    @Inject
    public DefaultAppliancesUseCaseImpl(ApplianceRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flowable<List<Appliance>> getAppliance() {
        return repository.getAllData().subscribeOn(Schedulers.io());
    }
}
