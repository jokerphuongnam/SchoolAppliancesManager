package com.example.schoolappliancesmanager.model.database.local;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

public interface DetailUsedLocal {
    Flowable<List<DetailUsed>> getAllData();

    Completable insert(DetailUsed detailUsed);

    Completable delete(DetailUsed detailUsed);

    Completable update(DetailUsed detailUsed);

    Flowable<List<DetailUsed>> filter(long from, long to);

    Flowable<List<ApplianceStatisticalByMonthTuple>> statisticalAppliancesByMonth(Long from, Long to);
}
