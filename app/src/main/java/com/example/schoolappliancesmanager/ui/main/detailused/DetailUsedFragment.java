package com.example.schoolappliancesmanager.ui.main.detailused;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentDetaiUsedBinding;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class DetailUsedFragment extends BaseFragment<FragmentDetaiUsedBinding, DetailUsedViewModel> {

    @Override
    public DetailUsedViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(DetailUsedViewModel.class);
    }

    public DetailUsedFragment() {
        super(R.layout.fragment_detai_used);
    }


    private DetailUsedAdapter adapter = null;

    @NonNull
    private DetailUsedAdapter getAdapter(){
        if(adapter == null){
            adapter = new DetailUsedAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.list.setLayoutManager(layoutManager);
        }
        return adapter;
    }

    @Override
    public void createView() {
        binding.list.setAdapter(getAdapter());
        viewModel.getData().observe(getViewLifecycleOwner(), detailUseds -> {
            getAdapter().submitList(detailUseds);
        });
        binding.addBtn.setOnClickListener(v -> {
            getAddPublisher().onNext(0);
        });
    }

    public PublishSubject<Integer> getAddPublisher() {
        if(addPublisher == null){
            addPublisher = PublishSubject.create();
        }
        return addPublisher;
    }

    private PublishSubject<Integer> addPublisher;

    public PublishSubject<Integer> getSelectPublisher() {
        if(selectPublisher == null){
            selectPublisher = PublishSubject.create();
        }
        return selectPublisher;
    }

    private PublishSubject<Integer> selectPublisher;

    @Override
    public void onResume() {
        super.onResume();
        getSelectPublisher().onNext(0);
    }
}
