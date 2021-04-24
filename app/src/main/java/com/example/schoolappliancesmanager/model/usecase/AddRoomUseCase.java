package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Single;

@Singleton
public interface AddRoomUseCase {
    Single<Boolean> addRoom(Room room);
    Single<Boolean> checkRoom(Room room);
    Single<Boolean> editRoom(Room room);
}
