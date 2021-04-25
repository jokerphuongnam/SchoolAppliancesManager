package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.repository.ApplianceRepository;
import com.example.schoolappliancesmanager.model.repository.DetailUsedRepository;
import com.example.schoolappliancesmanager.model.repository.RoomRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultBorrowUseCaseImpl implements BorrowUseCase {

    private final ApplianceRepository applianceRepository;
    private final DetailUsedRepository detailUsedRepository;
    private final RoomRepository roomRepository;

    @Inject
    public DefaultBorrowUseCaseImpl(ApplianceRepository applianceRepository, DetailUsedRepository detailUsedRepository, RoomRepository roomRepository) {
        this.applianceRepository = applianceRepository;
        this.detailUsedRepository = detailUsedRepository;
        this.roomRepository = roomRepository;
    }

    @Override
    public Completable borrow(DetailUsed detailUsed) {
        return detailUsedRepository.insert(detailUsed);
    }

    @Override
    public Completable edit(DetailUsed detailUsed) {
        return detailUsedRepository.update(detailUsed);
    }

    @Override
    public Flowable<List<Appliance>> getAppliances() {
        return applianceRepository.getNormalData().subscribeOn(Schedulers.io());
    }

    @Override
    public Flowable<List<Room>> getRoomNames() {
        return roomRepository.getAllData();
    }
}
