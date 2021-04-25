package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import java.util.List;

import javax.inject.Singleton;

import io.reactivex.rxjava3.core.Flowable;

@Singleton
public interface RoomsUseCase {
    Flowable<List<Room>> getRoom();
}
