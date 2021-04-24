package com.example.schoolappliancesmanager.ui.add.appliance;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.usecase.AddApplianceUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.SingleObserver;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class AddApplianceViewModel extends ViewModel {

    private AddApplianceUseCase useCase;

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

    private MutableLiveData<Boolean> success;

    public MutableLiveData<Boolean> getSuccess() {
        if (success == null) {
            success = new MutableLiveData<>();
        }
        return success;
    }

    public void addAppliance(Appliance appliance) {
        useCase.addAppliance(appliance).subscribe(new SingleObserver<Boolean>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                getSuccess().postValue(true);
                disposable.dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                disposable.dispose();
            }
        });
    }

    public void editAppliance(Appliance appliance) {
        useCase.editAppliance(appliance).subscribe(new SingleObserver<Boolean>() {
            private Disposable disposable;

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                disposable = d;
            }

            @Override
            public void onSuccess(@NonNull Boolean aBoolean) {
                getSuccess().postValue(true);
                disposable.dispose();
            }

            @Override
            public void onError(@NonNull Throwable e) {
                disposable.dispose();
            }
        });
    }
}
