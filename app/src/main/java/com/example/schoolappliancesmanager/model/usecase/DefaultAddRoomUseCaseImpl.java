package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Single;

public class DefaultAddRoomUseCaseImpl implements AddRoomUseCase {

    @Inject
    public DefaultAddRoomUseCaseImpl(){

    }

    @Override
    public Single<Boolean> addRoom(Room room) {
        return Single.just(true);
    }

    @Override
    public Single<Boolean> checkRoom(Room room) {
        return Single.just(true);
    }

    @Override
    public Single<Boolean> editRoom(Room room) {
        return Single.just(true);
    }
}
