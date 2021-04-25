package com.example.schoolappliancesmanager.ui.main.detailused;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.usecase.DetailUsedUseCase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

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

    public void initData() {
        composite.add(useCase.getDetailUsed().subscribe(detailUseds -> getData().postValue(detailUseds), Throwable::printStackTrace));
//        useCase.getDetailUsed().subscribe(new FlowableSubscriber<List<DetailUsed>>() {
//            @Override
//            public void onSubscribe(@NonNull Subscription s) {
//                subscription = s;
//            }
//
//            @Override
//            public void onNext(List<DetailUsed> detailUseds) {
//                data.postValue(detailUseds);
//            }
//
//            @Override
//            public void onError(Throwable t) {
//                t.printStackTrace();
//                subscription.cancel();
//            }
//
//            @Override
//            public void onComplete() {
//                subscription.cancel();
//            }
//        });
    }

    public void filter(long from, long to) {

    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        subscription.cancel();
        composite.dispose();
    }
}
