package com.example.schoolappliancesmanager.model.repository.Impl;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;
import com.example.schoolappliancesmanager.model.database.local.DetailUsedLocal;
import com.example.schoolappliancesmanager.model.repository.DetailUsedRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultDetailUsedRepositoryImpl implements DetailUsedRepository {
    private final DetailUsedLocal local;

    @Inject
    public DefaultDetailUsedRepositoryImpl(DetailUsedLocal local) {
        this.local = local;
    }

    @Override
    public Flowable<List<DetailUsed>> getAllData() {
        return local.getAllData().subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<DetailUsed>> filter(long from, long to) {
        return local.filter(from, to).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insert(DetailUsed detailUsed) {
        return local.insert(detailUsed).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable delete(DetailUsed detailUsed) {
        return local.delete(detailUsed).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable update(DetailUsed detailUsed) {
        return local.update(detailUsed).subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<ApplianceStatisticalByMonthTuple>> statisticalAppliancesByMonth(Long from, Long to) {
        return local.statisticalAppliancesByMonth(from, to);
    }
}
