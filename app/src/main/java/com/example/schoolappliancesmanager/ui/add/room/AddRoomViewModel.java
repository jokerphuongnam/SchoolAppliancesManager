package com.example.schoolappliancesmanager.ui.add.room;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.usecase.AddRoomUseCase;
import com.example.schoolappliancesmanager.util.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class AddRoomViewModel extends ViewModel {

    private AddRoomViewModel() {
    }

    private AddRoomUseCase useCase;

    @Inject
    public AddRoomViewModel(AddRoomUseCase useCase) {
        this.useCase = useCase;
    }

    private Room room;

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void initRoom(Room room) {
        if (room == null) {
            this.room = new Room();
        } else {
            this.room = room;
        }
    }

    private MutableLiveData<Resource<Boolean>> checkApplianceName;

    public MutableLiveData<Resource<Boolean>> getCheckRoomName() {
        if (checkApplianceName == null) {
            checkApplianceName = new MutableLiveData<>();
        }
        return checkApplianceName;
    }

    public void checkRoom(Room room) {
        useCase.checkRoom(room).subscribe(new SingleObserver<Boolean>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
                getCheckRoomName().postValue(new Resource.Loading<>());
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                disposable.dispose();
                getCheckRoomName().postValue(new Resource.Success<>(aBoolean));
            }

            @Override
            public void onError(@NonNull Throwable e) {
                disposable.dispose();
                getCheckRoomName().postValue(new Resource.Error<>(""));
            }
        });
    }

    private MutableLiveData<Boolean> success;

    public MutableLiveData<Boolean> getSuccess() {
        if (success == null) {
            success = new MutableLiveData<>();
        }
        return success;
    }

    public void addRoom(Room room) {
        useCase.addRoom(room).subscribe(new SingleObserver<Boolean>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                disposable.dispose();
                getSuccess().postValue(true);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                disposable.dispose();
            }
        });
    }

    public void editRoom(Room room) {
        useCase.editRoom(room).subscribe(new SingleObserver<Boolean>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                disposable.dispose();
                getSuccess().postValue(true);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                disposable.dispose();
            }
        });
    }
}
