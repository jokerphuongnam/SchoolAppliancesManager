package com.example.schoolappliancesmanager.ui.main.appliance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import java.util.List;

public class ApplianceViewModel extends ViewModel {
    private MutableLiveData<List<Appliance>> data = new MutableLiveData<>();
    public MutableLiveData<List<Appliance>> getData(){
        return data;
    }
}
