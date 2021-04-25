package com.example.schoolappliancesmanager.ui.main.detailused;

import android.app.DatePickerDialog;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentDetaiUsedBinding;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;
import com.example.schoolappliancesmanager.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.subjects.PublishSubject;

@AndroidEntryPoint
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
    private DetailUsedAdapter getAdapter() {
        if (adapter == null) {
            adapter = new DetailUsedAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.list.setLayoutManager(layoutManager);
        }
        return adapter;
    }

    private void setUpFilter() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        binding.dateUsedFrom.setText(simpleDateFormat.format(date));
        binding.dateUsedTo.setText(simpleDateFormat.format(date));
        binding.filter.setOnClickListener((v) -> {
            long from = DateUtil.stringDateToLong(binding.dateUsedFrom.getText().toString());
            long to = DateUtil.stringDateToLong(binding.dateUsedTo.getText().toString());
            viewModel.filter(from, to);
        });
        binding.calendarChooseFrom.setOnClickListener((v) -> {
            isFrom = true;
            getDatePicker().show();
        });
        binding.calendarChooseTo.setOnClickListener((v) -> {
            isFrom = false;
            getDatePicker().show();
        });
    }

    private boolean isFrom = false;

    private DatePickerDialog.OnDateSetListener fromDatePickerCallBack;
    private DatePickerDialog.OnDateSetListener toDatePickerCallBack;

    @Override
    protected DatePickerDialog.OnDateSetListener getDatePickerCallBack() {
        if (isFrom) {
            if (fromDatePickerCallBack == null) {
                fromDatePickerCallBack = (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    binding.dateUsedFrom.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.getTime()));
                };
            }
        } else {
            if (toDatePickerCallBack == null) {
                toDatePickerCallBack = (view, year, monthOfYear, dayOfMonth) -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    binding.dateUsedTo.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.getTime()));
                };
            }
        }
        return isFrom ? fromDatePickerCallBack : toDatePickerCallBack;
    }

    @Override
    public void createView() {
        binding.list.setAdapter(getAdapter());
        setUpFilter();
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
        viewModel.initData();
    }
}
