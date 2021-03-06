package com.example.schoolappliancesmanager.ui.add.borrow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.model.usecase.BorrowUseCase;
import com.example.schoolappliancesmanager.util.Resource;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class BorrowViewModel extends ViewModel {

    private final BorrowUseCase useCase;

    @Inject
    public BorrowViewModel(BorrowUseCase useCase) {
        this.useCase = useCase;
    }

    public DetailUsed getDetailUsed() {
        return detailUsed;
    }

    public void setDetailUsed(DetailUsed detailUsed) {
        this.detailUsed = detailUsed;
    }

    private DetailUsed detailUsed;

    public void initData(DetailUsed detailUsed) {
        if (detailUsed == null) {
            this.detailUsed = new DetailUsed();
        } else {
            this.detailUsed = detailUsed;
        }
    }

    private final CompositeDisposable composite = new CompositeDisposable();

    public void initApplianceAndRoomName() {
        composite.add(useCase.getAppliances(detailUsed.getApplianceId()).subscribe(appliances -> getAppliances().postValue(appliances), Throwable::printStackTrace));
        composite.add(useCase.getRoomNames().subscribe(roomNames -> getRooms().postValue(roomNames), Throwable::printStackTrace));
    }

    private MutableLiveData<List<Appliance>> appliances;
    private MutableLiveData<List<Room>> rooms;

    public MutableLiveData<List<Appliance>> getAppliances() {
        if (appliances == null) {
            appliances = new MutableLiveData<>();
            appliances.postValue(new ArrayList<>());
        }
        return appliances;
    }

    public MutableLiveData<List<Room>> getRooms() {
        if (rooms == null) {
            rooms = new MutableLiveData<>();
            rooms.postValue(new ArrayList<>());
        }
        return rooms;
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
                    getSuccess().postValue(new Resource.Loading<>());
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

    public void borrow(DetailUsed detailUsed) {
        useCase.borrow(detailUsed).subscribe(getCompletableObserver());
    }

    public void edit(DetailUsed detailUsed) {
        useCase.edit(detailUsed).subscribe(getCompletableObserver());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        composite.dispose();
//        applianceSubscription.cancel();
//        roomNameSubscription.cancel();
    }
}
