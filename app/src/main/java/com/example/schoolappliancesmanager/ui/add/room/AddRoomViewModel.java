package com.example.schoolappliancesmanager.ui.add.room;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.usecase.AddRoomUseCase;
import com.example.schoolappliancesmanager.util.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class AddRoomViewModel extends ViewModel {

    private final AddRoomUseCase useCase;

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
                if (aBoolean) {
                    getCheckRoomName().postValue(new Resource.Error<>(""));
                } else {
                    getCheckRoomName().postValue(new Resource.Success<>(aBoolean));
                }
            }

            @Override
            public void onError(@NonNull Throwable e) {
                disposable.dispose();
            }
        });
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

    public void addRoom(Room room) {
        useCase.addRoom(room).subscribe(getCompletableObserver());
    }

    public void editRoom(Room room) {
        useCase.editRoom(room).subscribe(getCompletableObserver());
    }
}
