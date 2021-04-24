package com.example.schoolappliancesmanager.ui.main.borrow;

import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentBorrowBinding;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

import io.reactivex.rxjava3.subjects.PublishSubject;

public class BorrowFragment extends BaseFragment<FragmentBorrowBinding, BorrowViewModel> {

    @Override
    public BorrowViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(BorrowViewModel.class);
    }

    public BorrowFragment() {
        super(R.layout.fragment_borrow);
    }

    @Override
    public void createView() {

    }

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
    }}
