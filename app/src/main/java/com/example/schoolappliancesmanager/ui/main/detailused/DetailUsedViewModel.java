package com.example.schoolappliancesmanager.ui.main.detailused;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;

import java.util.List;

public class DetailUsedViewModel extends ViewModel {
    private MutableLiveData<List<DetailUsed>> data = new MutableLiveData<>();
    public MutableLiveData<List<DetailUsed>> getData(){
        return data;
    }
}
