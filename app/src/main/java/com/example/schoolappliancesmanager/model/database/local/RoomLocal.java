package com.example.schoolappliancesmanager.model.database.local;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public interface RoomLocal {
    Flowable<List<Room>> getAllData();

    /**
     * for test data
     * */
    List<Room> getAll();

    Completable insert(Room room);

    Completable delete(Room room);

    Completable update(Room room);

    Single<Boolean> isNameExists(String name);
}
