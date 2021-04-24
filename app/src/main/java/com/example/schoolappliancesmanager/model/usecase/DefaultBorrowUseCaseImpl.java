package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class DefaultBorrowUseCaseImpl implements BorrowUseCase {

    @Inject
    public DefaultBorrowUseCaseImpl() {
    }

    @Override
    public Single<Boolean> borrow(DetailUsed detailUsed) {
        return Single.just(true);
    }
}
