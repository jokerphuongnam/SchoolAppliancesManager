package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import javax.inject.Singleton;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Singleton
public interface AddRoomUseCase {
    @NonNull Completable addRoom(Room room);
    Single<Boolean> checkRoom(Room room);
    @NonNull Completable editRoom(Room room);
}
