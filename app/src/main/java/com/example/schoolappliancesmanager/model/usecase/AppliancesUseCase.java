package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Flowable;

@Singleton
public interface AppliancesUseCase {
    Flowable<List<Appliance>> getAppliance();
}
