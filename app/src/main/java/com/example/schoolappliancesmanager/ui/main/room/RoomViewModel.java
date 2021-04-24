package com.example.schoolappliancesmanager.ui.main.room;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class RoomViewModel extends ViewModel {

    @Inject
    public RoomViewModel() {
    }

    private MutableLiveData<List<Room>> data = new MutableLiveData<>();
    public MutableLiveData<List<Room>> getData(){
        return data;
    }
}
