package com.example.schoolappliancesmanager.model.repository;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Singleton
public interface RoomRepository {
    Flowable<List<Room>> getAllData();

    Completable insert(Room room);

    Completable delete(Room room);

    Completable update(Room room);

    Single<Boolean> isNameExists(String name);
}
