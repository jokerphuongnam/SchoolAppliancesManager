package com.example.schoolappliancesmanager.model.usecase;

import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;

import java.util.List;

import io.reactivex.rxjava3.core.Flowable;

public interface ApplianceStatisticalChartUseCase {
    Flowable<List<ApplianceStatisticalByMonthTuple>> statisticalByMonth(Integer year, Integer month);
}
