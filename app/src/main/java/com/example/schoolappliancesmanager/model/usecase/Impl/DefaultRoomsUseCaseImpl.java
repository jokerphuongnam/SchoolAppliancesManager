package com.example.schoolappliancesmanager.model.usecase.Impl;

import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.repository.RoomRepository;
import com.example.schoolappliancesmanager.model.usecase.RoomsUseCase;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultRoomsUseCaseImpl implements RoomsUseCase {

    private final RoomRepository repository;

    @Inject
    public DefaultRoomsUseCaseImpl(RoomRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flowable<List<Room>> getRoom() {
        return repository.getAllData().subscribeOn(Schedulers.io());
    }

    @Override
    public Completable deleteRoom(Room room) {
        return repository.delete(room).subscribeOn(Schedulers.io());
    }
}
