package com.example.schoolappliancesmanager.ui.add;

import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class AddViewModel extends ViewModel {

    @Inject
    public AddViewModel() {
    }

    private AddActivity.TypeUpdate typeUpdate;

    public AddActivity.TypeUpdate getTypeUpdate() {
        return typeUpdate;
    }

    public void setTypeUpdate(AddActivity.TypeUpdate typeUpdate) {
        this.typeUpdate = typeUpdate;
    }

    public AddActivity.TypeAction getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(AddActivity.TypeAction typeAction) {
        this.typeAction = typeAction;
    }

    private AddActivity.TypeAction typeAction;
}
