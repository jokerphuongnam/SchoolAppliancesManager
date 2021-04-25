package com.example.schoolappliancesmanager.model.database.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface RoomDao extends RoomLocal {
    @Query("SELECT * FROM ROOMS")
    @Override
    Flowable<List<Room>> getAllData();

    @Query("SELECT * FROM ROOMS")
    @Override
    List<Room> getAll();

    @Insert
    @Override
    Completable insert(Room room);

    @Delete
    @Override
    Completable delete(Room room);

    @Update
    @Override
    Completable update(Room room);

    @Query("SELECT EXISTS(SELECT * FROM ROOMS WHERE room_id = :name)")
    @Override
    Single<Boolean> isNameExists(String name);
}
