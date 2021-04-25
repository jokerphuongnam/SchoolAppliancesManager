package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.Room;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Singleton
public interface BorrowUseCase {
    Completable borrow(DetailUsed detailUsed);

    Completable edit(DetailUsed detailUsed);

    Flowable<List<Appliance>> getAppliances();

    Flowable<List<Room>> getRoomNames();
}
