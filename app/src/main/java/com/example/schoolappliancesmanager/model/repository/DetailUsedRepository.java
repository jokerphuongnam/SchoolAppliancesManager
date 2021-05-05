package com.example.schoolappliancesmanager.model.repository;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Singleton
public interface DetailUsedRepository {
    Flowable<List<DetailUsed>> getAllData();

    Flowable<List<DetailUsed>> filter(long from, long to);

    Completable insert(DetailUsed detailUsed);

    Completable delete(DetailUsed detailUsed);

    Completable update(DetailUsed detailUsed);

    Flowable<List<ApplianceStatisticalByMonthTuple>> statisticalAppliancesByMonth(Long from, Long to);

}
