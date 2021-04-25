package com.example.schoolappliancesmanager.ui.main.room;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.usecase.RoomsUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
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

    public void initData() {
        disposable = useCase.getRoom().subscribe((rooms) -> getData().postValue(rooms), Throwable::printStackTrace);
//        useCase.getRoom().subscribe(new FlowableSubscriber<List<Room>>() {
//            @Override
//            public void onSubscribe(@NonNull Subscription s) {
//                subscription = s;
//            }
//
//            @Override
//            public void onNext(List<Room> rooms) {
//                data.postValue(rooms);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                subscription.cancel();
//                t.printStackTrace();
//            }
//
//            @Override
//            public void onComplete() {
//                subscription.cancel();
//            }
//        });
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
