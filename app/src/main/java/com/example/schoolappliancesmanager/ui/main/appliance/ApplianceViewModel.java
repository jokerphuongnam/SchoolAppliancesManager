package com.example.schoolappliancesmanager.ui.main.appliance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.usecase.AppliancesUseCase;
import com.example.schoolappliancesmanager.util.Resource;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

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

    @Inject
    Gson gson;

    public void initData() {
        composite.add(useCase.getAppliance().subscribe(appliances -> {
            String appliancesJson = gson.toJson(appliances);
            List<Appliance> appliancesClone = gson.fromJson(appliancesJson, new TypeToken<List<Appliance>>() {
            }.getType());
            getData().postValue(appliancesClone);
        }, Throwable::printStackTrace));
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

    public void deleteAppliance(Appliance appliance){
        useCase.deleteAppliance(appliance).subscribe(getCompletableObserver());
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        subscription.cancel();
    }
}
