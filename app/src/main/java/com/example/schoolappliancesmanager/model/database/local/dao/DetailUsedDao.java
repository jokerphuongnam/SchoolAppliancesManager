package com.example.schoolappliancesmanager.model.database.local.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;
import com.example.schoolappliancesmanager.model.database.local.DetailUsedLocal;

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

    @Delete
    @Override
    Completable delete(DetailUsed detailUsed);

    @Update
    @Override
    Completable update(DetailUsed detailUsed);

    @Query("SELECT * FROM DETAIL_USED WHERE date_used BETWEEN :from AND :to")
    @Override
    Flowable<List<DetailUsed>> filter(long from, long to);

    @Query("SELECT DETAIL_USED.appliance_id, APPLIANCES.appliance_name, APPLIANCES.dir_image, COUNT(DETAIL_USED.appliance_id) AS 'quantity' " +
            "FROM  DETAIL_USED, APPLIANCES " +
            "WHERE  detail_used.appliance_id = APPLIANCES.appliance_id AND ((:from IS NULL OR date_used >= :from OR :from = 0) AND (:to IS NULL OR date_used <= :to OR :to = 0)) " +
            "GROUP BY DETAIL_USED.appliance_id " +
            "LIMIT 10")
    @Override
    Flowable<List<ApplianceStatisticalByMonthTuple>> statisticalAppliancesByMonth(Long from, Long to);
}
