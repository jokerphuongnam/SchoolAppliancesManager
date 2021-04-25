package com.example.schoolappliancesmanager.ui.main.appliance;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.local.ApplianceLocal;
import com.example.schoolappliancesmanager.model.usecase.AppliancesUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@HiltViewModel
public class ApplianceViewModel extends ViewModel {

    private final AppliancesUseCase useCase;

    private final CompositeDisposable composite = new CompositeDisposable();

    @Inject
    public ApplianceViewModel(AppliancesUseCase useCase) {
        this.useCase = useCase;
    }

    private MutableLiveData<List<Appliance>> data = null;

    public MutableLiveData<List<Appliance>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            data.postValue(new ArrayList<>());
        }
        return data;
    }

//    private Subscription subscription;

    @Inject
    ApplianceLocal local;

    public void initData() {
        composite.add(useCase.getAppliance().subscribe(appliances -> getData().postValue(appliances), Throwable::printStackTrace));
//        useCase.getAppliance().subscribe(new FlowableSubscriber<List<Appliance>>() {
//            @Override
//            public void onSubscribe(@NonNull Subscription s) {
//                subscription = s;
//            }
//
//            @Override
//            public void onNext(List<Appliance> appliances) {
//                data.postValue(appliances);
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
//        subscription.cancel();
    }
}
