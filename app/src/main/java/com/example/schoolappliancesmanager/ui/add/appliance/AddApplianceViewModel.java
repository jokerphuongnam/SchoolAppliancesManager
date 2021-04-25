package com.example.schoolappliancesmanager.ui.add.appliance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.usecase.AddApplianceUseCase;
import com.example.schoolappliancesmanager.util.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.CompletableObserver;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class AddApplianceViewModel extends ViewModel {

    private final AddApplianceUseCase useCase;

    @Inject
    public AddApplianceViewModel(AddApplianceUseCase useCase) {
        this.useCase = useCase;
    }

    public Appliance getAppliance() {
        return appliance;
    }

    public void setAppliance(Appliance appliance) {
        this.appliance = appliance;
    }

    private Appliance appliance;

    public void initAppliance(Appliance appliance) {
        if (appliance == null) {
            this.appliance = new Appliance();
        } else {
            this.appliance = appliance;
        }
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

    public void addAppliance(Appliance appliance) {
        useCase.addAppliance(appliance).subscribe(getCompletableObserver());
    }

    public void editAppliance(Appliance appliance) {
        useCase.editAppliance(appliance).subscribe(getCompletableObserver());
    }
}
