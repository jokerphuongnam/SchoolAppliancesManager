package com.example.schoolappliancesmanager.ui.chart;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;
import com.example.schoolappliancesmanager.model.usecase.ApplianceStatisticalChartUseCase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class StatisticalChartViewModel extends ViewModel {

    private final ApplianceStatisticalChartUseCase useCase;

    @Inject
    public StatisticalChartViewModel(ApplianceStatisticalChartUseCase useCase) {
        this.useCase = useCase;
    }

    private MutableLiveData<List<ApplianceStatisticalByMonthTuple>> data = null;

    public MutableLiveData<List<ApplianceStatisticalByMonthTuple>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            data.postValue(new ArrayList<>());
        }
        return data;
    }

    private Disposable disposable = null;

    @Inject
    Gson gson;

    private Integer year = null;
    private Integer month = null;

    public void setTime(Integer year, Integer month) {
        this.year = year;
        this.month = month;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getMonth() {
        return month;
    }

    public void statisticalByMonth() {
        if (disposable != null) {
            disposable.dispose();
        }
        disposable = useCase.statisticalByMonth(year, month).subscribe(appliancesStatisticalByMonthTuples -> {
            if (!appliancesStatisticalByMonthTuples.get(0).equals(new ApplianceStatisticalByMonthTuple())) {
                String roomsJson = gson.toJson(appliancesStatisticalByMonthTuples);
                List<ApplianceStatisticalByMonthTuple> applianceStatisticalByMonthTupleClone = gson.fromJson(roomsJson, new TypeToken<List<ApplianceStatisticalByMonthTuple>>() {
                }.getType());
                getData().postValue(applianceStatisticalByMonthTupleClone);
            } else {
                getData().postValue(new ArrayList<>());
            }
        }, Throwable::printStackTrace);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
