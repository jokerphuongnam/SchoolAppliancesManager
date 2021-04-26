package com.example.schoolappliancesmanager.ui.main.room;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.usecase.RoomsUseCase;
import com.example.schoolappliancesmanager.util.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class RoomViewModel extends ViewModel {

    private final RoomsUseCase useCase;

    @Inject
    public RoomViewModel(RoomsUseCase useCase) {
        this.useCase = useCase;
    }

    private MutableLiveData<List<Room>> data = null;

    public MutableLiveData<List<Room>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            data.postValue(new ArrayList<>());
        }
        return data;
    }

    private Disposable disposable;

    @Inject
    Gson gson;

    public void initData() {
        disposable = useCase.getRoom().subscribe((rooms) -> {
            String roomsJson = gson.toJson(rooms);
            List<Room> roomsClone = gson.fromJson(roomsJson, new TypeToken<List<Room>>() {
            }.getType());
            getData().postValue(roomsClone);
        }, Throwable::printStackTrace);
    }

    private MutableLiveData<Resource<Boolean>> success;

    public MutableLiveData<Resource<Boolean>> getSuccess() {
        if (success == null) {
            success = new MutableLiveData<>();
        }
        return success;
    }

    private CompletableObserver completableObserver;

    private CompletableObserver getCompletableObserver() {
        if (completableObserver == null) {
            completableObserver = new CompletableObserver() {
                private Disposable disposable;

                @Override
                public void onSubscribe(@NonNull Disposable d) {
                    disposable = d;
                    getSuccess().postValue(new Resource.Loading<>(true));
                }

                @Override
                public void onComplete() {
                    disposable.dispose();
                    getSuccess().postValue(new Resource.Success<>(true));
                }

                @Override
                public void onError(@NonNull Throwable e) {
                    disposable.dispose();
                    e.printStackTrace();
                    getSuccess().postValue(new Resource.Error<>(""));
                }
            };
        }
        return completableObserver;
    }

    public void deleteRoom(Room room){
        useCase.deleteRoom(room).subscribe(getCompletableObserver());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
