package com.example.schoolappliancesmanager.ui.main.detailused;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.usecase.DetailUsedUseCase;
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
public class DetailUsedViewModel extends ViewModel {

    private final DetailUsedUseCase useCase;

    private final CompositeDisposable composite = new CompositeDisposable();

    @Inject
    public DetailUsedViewModel(DetailUsedUseCase useCase) {
        this.useCase = useCase;
    }

    private MutableLiveData<List<DetailUsed>> data = null;

    public MutableLiveData<List<DetailUsed>> getData() {
        if (data == null) {
            data = new MutableLiveData<>();
            data.postValue(new ArrayList<>());
        }
        return data;
    }

//    private Subscription subscription;

    private Disposable dataDisposable;

    @Inject
    Gson gson;

    public void initData() {
        if (dataDisposable != null) {
            composite.remove(dataDisposable);
            dataDisposable.dispose();
        }
        if(filterDisposable != null){
            composite.remove(filterDisposable);
            filterDisposable.dispose();
        }
        dataDisposable = useCase.getDetailUsed().subscribe(detailUseds -> {
            String detailUsedJson = gson.toJson(detailUseds);
            List<DetailUsed> detailUsedsClone = gson.fromJson(detailUsedJson, new TypeToken<List<DetailUsed>>() {
            }.getType());
            getData().postValue(detailUsedsClone);
        }, Throwable::printStackTrace);
        composite.add(dataDisposable);
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

    public void deleteDetailUsed(DetailUsed detailUsed){
        useCase.deleteDetailUsed(detailUsed).subscribe(getCompletableObserver());
    }

    private Disposable filterDisposable;

    public void filter(long from, long to) {
        if (dataDisposable != null) {
            composite.remove(dataDisposable);
            dataDisposable.dispose();
        }
        if(filterDisposable != null){
            composite.remove(filterDisposable);
            filterDisposable.dispose();
        }
        filterDisposable = useCase.filter(from, to).subscribe(detailUseds -> {
            String detailUsedJson = gson.toJson(detailUseds);
            List<DetailUsed> detailUsedsClone = gson.fromJson(detailUsedJson, new TypeToken<List<DetailUsed>>() {
            }.getType());
            getData().postValue(detailUsedsClone);
        }, Throwable::printStackTrace);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        subscription.cancel();
        composite.dispose();
    }
}
