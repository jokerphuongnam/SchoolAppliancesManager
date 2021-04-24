package com.example.schoolappliancesmanager.ui.main.room;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Room;

import java.util.List;

public class RoomViewModel extends ViewModel {
    private MutableLiveData<List<Room>> data = new MutableLiveData<>();
    public MutableLiveData<List<Room>> getData(){
        return data;
    }
}
