package com.example.schoolappliancesmanager.ui.main.borrow;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentBorrowBinding;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.subjects.PublishSubject;

@AndroidEntryPoint
public class BorrowFragment extends BaseFragment<FragmentBorrowBinding, BorrowViewModel> {

    @Override
    public BorrowViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(BorrowViewModel.class);
    }

    public BorrowFragment() {
        super(R.layout.fragment_borrow);
    }

    private final DatePickerDialog.OnDateSetListener datePickerCallBack = (view, year, monthOfYear, dayOfMonth) -> {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, monthOfYear);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        DetailUsed detailUsed = viewModel.getDetailUsedMutableLiveData().getValue();
        detailUsed.setDateUsed(calendar.getTimeInMillis());
        viewModel.setData(detailUsed);
    };

    @NonNull
    private DatePickerDialog getDatePicker() {
        Calendar calendar = Calendar.getInstance();
        return new DatePickerDialog(
                getContext(),
                datePickerCallBack,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
    }

    private ArrayAdapter<String> applianceAdapter;
    private ArrayAdapter<String> roomAdapter;

    public ArrayAdapter<String> getApplianceAdapter() {
        if (applianceAdapter == null) {
            applianceAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        }
        return applianceAdapter;
    }

    public ArrayAdapter<String> getRoomAdapter() {
        if (roomAdapter == null) {
            roomAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item);
        }
        return roomAdapter;
    }

    private void initAdapterSpinner() {
        binding.applianceSpinner.setAdapter(getApplianceAdapter());
        binding.roomSpinner.setAdapter(getRoomAdapter());
    }

    @Override
    public void createView() {
        initAdapterSpinner();
        binding.calendarChoose.setOnClickListener((v -> {
            getDatePicker().show();
        }));
        viewModel.setData(new DetailUsed());
        binding.setDetailUsed(viewModel.getDetailUsedMutableLiveData().getValue());
        viewModel.initApplianceAndRoomName();
        viewModel.getDetailUsedMutableLiveData().observe(getViewLifecycleOwner(), detailUsed -> {
            binding.setDetailUsed(detailUsed);
        });
        viewModel.getAppliances().observe(getViewLifecycleOwner(), (appliances) -> {
            getApplianceAdapter().notifyDataSetChanged();
            binding.applianceSpinner.setAdapter(getApplianceAdapter());
        });
        viewModel.getRooms().observe(getViewLifecycleOwner(), (rooms) -> {
            getRoomAdapter().notifyDataSetChanged();
            binding.roomSpinner.setAdapter(getRoomAdapter());
        });
        binding.borrow.setOnClickListener((v -> {
            if (binding.getDetailUsed().getClassName().isEmpty()) {
                binding.classNameError.setVisibility(View.VISIBLE);
            } else {
                viewModel.borrow(binding.getDetailUsed());
                binding.classNameError.setVisibility(View.GONE);
            }
        }));
//        Calendar calendar = Calendar.getInstance();
//        calendar.setTimeZone(TimeZone.getDefault());
//        binding.dateUsed.setText(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(calendar.getTime()));
    }

    public PublishSubject<Integer> getSelectPublisher() {
        if (selectPublisher == null) {
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
