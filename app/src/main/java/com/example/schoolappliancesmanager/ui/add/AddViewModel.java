package com.example.schoolappliancesmanager.ui.add;

import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.ui.main.MainActivity;

public class AddViewModel extends ViewModel {
    public MainActivity.TypeUpdate getTypeUpdate() {
        return typeUpdate;
    }

    public void setTypeUpdate(MainActivity.TypeUpdate typeUpdate) {
        this.typeUpdate = typeUpdate;
    }

    private MainActivity.TypeUpdate typeUpdate;

}
