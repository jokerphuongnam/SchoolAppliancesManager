package com.example.schoolappliancesmanager.model.repository.Impl;

import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.database.local.RoomLocal;
import com.example.schoolappliancesmanager.model.repository.RoomRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DefaultRoomRepositoryImpl implements RoomRepository {
    private final RoomLocal local;

    @Inject
    public DefaultRoomRepositoryImpl(RoomLocal local) {
        this.local = local;
    }

    @Override
    public Flowable<List<Room>> getAllData() {
        return local.getAllData().subscribeOn(Schedulers.io());
    }

    @Override
    public Completable insert(Room room) {
        return local.insert(room).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable delete(Room room) {
        return local.delete(room).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable update(Room room) {
        return local.update(room).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> isNameExists(String name) {
        return local.isNameExists(name).subscribeOn(Schedulers.io());
    }
}
