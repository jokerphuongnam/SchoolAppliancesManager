package com.example.schoolappliancesmanager.ui.main.borrow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.usecase.BorrowUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class BorrowViewModel extends ViewModel {

    private BorrowUseCase useCase;

    @Inject
    public BorrowViewModel(BorrowUseCase useCase) {
        this.useCase = useCase;
    }

    private MutableLiveData<DetailUsed> detailUsedMutableLiveData;

    public MutableLiveData<DetailUsed> getDetailUsedMutableLiveData() {
        if(detailUsedMutableLiveData == null){
            detailUsedMutableLiveData = new MutableLiveData<>();
        }
        return detailUsedMutableLiveData;
    }

    public void setData(DetailUsed detailUsed){
        getDetailUsedMutableLiveData().postValue(detailUsed);
    }

    public void initApplianceAndRoomName(){

    }

    private MutableLiveData<List<String>> appliances;
    private MutableLiveData<List<String>> rooms;

    public MutableLiveData<List<String>> getAppliances() {
        if(appliances == null){
            appliances = new MutableLiveData<>();
        }
        return appliances;
    }

    public MutableLiveData<List<String>> getRooms() {
        if(rooms == null){
            rooms = new MutableLiveData<>();
        }
        return rooms;
    }

    private MutableLiveData<Boolean> success;

    public MutableLiveData<Boolean> getSuccess() {
        if (success == null) {
            success = new MutableLiveData<>();
        }
        return success;
    }

    public void borrow(DetailUsed detailUsed){
        useCase.borrow(detailUsed).subscribe(new SingleObserver<Boolean>() {
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
