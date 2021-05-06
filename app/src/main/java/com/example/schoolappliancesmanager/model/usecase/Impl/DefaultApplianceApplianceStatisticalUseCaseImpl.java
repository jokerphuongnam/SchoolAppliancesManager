package com.example.schoolappliancesmanager.model.usecase.Impl;

import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;
import com.example.schoolappliancesmanager.model.repository.DetailUsedRepository;
import com.example.schoolappliancesmanager.model.usecase.ApplianceStatisticalUseCase;

import java.time.LocalDate;
import java.time.Year;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Flowable;

public class DefaultApplianceApplianceStatisticalUseCaseImpl implements ApplianceStatisticalUseCase {

    private final DetailUsedRepository repository;

    @Inject
    public DefaultApplianceApplianceStatisticalUseCaseImpl(DetailUsedRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flowable<List<ApplianceStatisticalByMonthTuple>> statisticalByMonth(Integer year, Integer month) {
        if (year != null && year != 0) {
            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(year, month, 1, 0, 0, 0);

            int lastDayOfMonth = LocalDate.of(year, month + 1, 1).getMonth().length(Year.of(year).isLeap());
            Calendar endCalendar = Calendar.getInstance();
            //assuming year/month/date information is not important
            endCalendar.set(year, month , lastDayOfMonth, 0, 0, 0);
            return repository.statisticalAppliancesByMonth(startCalendar.getTimeInMillis(), endCalendar.getTimeInMillis());
        }
        return repository.statisticalAppliancesByMonth(null, null);
    }
}
