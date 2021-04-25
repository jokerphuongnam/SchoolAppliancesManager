package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.repository.RoomRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultAddRoomUseCaseImpl implements AddRoomUseCase {

    private final RoomRepository repository;

    @Inject
    public DefaultAddRoomUseCaseImpl(RoomRepository repository) {
        this.repository = repository;
    }


    @Override
    public @NonNull Completable addRoom(Room room) {
        return repository.insert(room).subscribeOn(Schedulers.io());
    }

    @Override
    public @NonNull Completable editRoom(Room room) {
        return repository.update(room).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> checkRoom(Room room) {
        return repository.isNameExists(room.getRoomName()).subscribeOn(Schedulers.io());
    }
}
