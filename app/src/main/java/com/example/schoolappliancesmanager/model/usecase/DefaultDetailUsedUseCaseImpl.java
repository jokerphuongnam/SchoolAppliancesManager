package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.repository.DetailUsedRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultDetailUsedUseCaseImpl implements DetailUsedUseCase {

    private final DetailUsedRepository repository;

    @Inject
    public DefaultDetailUsedUseCaseImpl(DetailUsedRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flowable<List<DetailUsed>> getDetailUsed() {
        return repository.getAllData().subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<DetailUsed>> filter(long from, long to) {
        return repository.filter(from, to).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteDetailUsed(DetailUsed detailUsed) {
        return repository.delete(detailUsed).subscribeOn(Schedulers.io());
    }
}
