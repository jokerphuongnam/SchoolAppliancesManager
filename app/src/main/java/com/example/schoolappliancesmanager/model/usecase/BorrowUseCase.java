package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public interface BorrowUseCase {
    Single<Boolean> borrow(DetailUsed detailUsed);
}
