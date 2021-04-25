package com.example.schoolappliancesmanager.model.database.local;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;

@Dao
public interface DetailUsedDao extends DetailUsedLocal {
    @Query("SELECT * FROM DETAIL_USED")
    @Override
    Flowable<List<DetailUsed>> getAllData();

    @Insert
    @Override
    Completable insert(DetailUsed detailUsed);

    @Update
    @Override
    Completable delete(DetailUsed detailUsed);

    @Delete
    @Override
    Completable update(DetailUsed detailUsed);

    @Query("SELECT * FROM DETAIL_USED WHERE date_used BETWEEN :from AND :to")
    @Override
    Flowable<List<DetailUsed>> filter(long from, long to);
}
