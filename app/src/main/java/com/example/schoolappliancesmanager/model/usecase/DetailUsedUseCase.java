package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Singleton
public interface DetailUsedUseCase {
    Flowable<List<DetailUsed>> getDetailUsed();

    Flowable<List<DetailUsed>> filter(long from, long to);

    Completable deleteDetailUsed(DetailUsed detailUsed);

    Completable returnAppliance(DetailUsed detailUsed);
}
