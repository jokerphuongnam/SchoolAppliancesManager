package com.example.schoolappliancesmanager.ui.main.borrow;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentBorrowBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;
import com.example.schoolappliancesmanager.util.Resource;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;

@AndroidEntryPoint
public class BorrowFragment extends BaseFragment<FragmentBorrowBinding, BorrowViewModel> {

    @Override
    public BorrowViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(BorrowViewModel.class);
    }

    public BorrowFragment() {
        super(R.layout.fragment_borrow);
    }

    private DatePickerDialog.OnDateSetListener datePickerCallBack;

    @Override
    protected DatePickerDialog.OnDateSetListener getDatePickerCallBack() {
        if (datePickerCallBack == null) {
            datePickerCallBack = (view, year, monthOfYear, dayOfMonth) -> {
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, monthOfYear);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                DetailUsed detailUsed = viewModel.getDetailUsedMutableLiveData().getValue();
                detailUsed.setDateUsed(calendar.getTimeInMillis());
                viewModel.getDetailUsedMutableLiveData().postValue(detailUsed);
            };
        }
        return datePickerCallBack;
    }

    public ArrayAdapter<String> getApplianceAdapter() {
        List<String> appliances;
        if (viewModel.getAppliances().getValue() == null || viewModel.getAppliances().getValue().isEmpty()) {
            appliances = new ArrayList<>();
        } else {
            appliances = viewModel.getAppliances().getValue().stream().map(Appliance::getApplianceName).collect(Collectors.toList());
        }
        return new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, appliances);
    }

    public ArrayAdapter<String> getRoomAdapter() {
        List<String> rooms;
        if (viewModel.getRooms().getValue() == null || viewModel.getRooms().getValue().isEmpty()) {
            rooms = new ArrayList<>();
        } else {
            rooms = viewModel.getRooms().getValue().stream().map(Room::getRoomName).collect(Collectors.toList());
        }
        return new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, rooms);
    }

    private void initAdapterSpinner() {
        binding.applianceSpinner.setAdapter(getApplianceAdapter());
        binding.roomSpinner.setAdapter(getRoomAdapter());
    }

    private void initData() {
        Bundle arguments = getArguments();
        if (arguments != null) {
            boolean hasData = arguments.getSerializable(DATA) == null;
            binding.roomSpinner.setEnabled(hasData);
            binding.applianceSpinner.setEnabled(hasData);
            viewModel.setData((DetailUsed) arguments.getSerializable(DATA));
        } else {
            viewModel.setData(null);
            binding.roomSpinner.setEnabled(true);
            binding.applianceSpinner.setEnabled(true);
        }
    }

    @Override
    public void createView() {
        initAdapterSpinner();
        binding.calendarChoose.setOnClickListener((v -> {
            getDatePicker().show();
        }));
        initData();
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
        viewModel.getSuccess().observe(getViewLifecycleOwner(), resource -> {
            if (resource instanceof Resource.Loading) {
                binding.borrowError.setVisibility(View.GONE);
            } else if (resource instanceof Resource.Success) {
                showToast(getString(R.string.save_success));
            } else if (resource instanceof Resource.Error) {
                binding.borrowError.setText(R.string.appliance_borrowed);
                binding.borrowError.setVisibility(View.VISIBLE);
            }
        });
        binding.borrow.setOnClickListener((v) -> {
            if (viewModel.getRooms().getValue().isEmpty()) {
                showToast(getString(R.string.room_empty));
            } else if (viewModel.getAppliances().getValue().isEmpty()) {
                showToast(getString(R.string.appliance_empty));
            } else {
                DetailUsed detailUsed = binding.getDetailUsed();
                if (detailUsed.getClassName().isEmpty()) {
                    binding.borrowError.setVisibility(View.VISIBLE);
                    binding.borrowError.setText(R.string.class_name_empty);
                } else {
                    binding.borrowError.setVisibility(View.GONE);
                    detailUsed.setApplianceId(viewModel.getAppliances().getValue().get(binding.applianceSpinner.getSelectedItemPosition()).getApplianceId());
                    detailUsed.setRoomName(viewModel.getRooms().getValue().get(binding.roomSpinner.getSelectedItemPosition()).getRoomName());
                    switch (viewModel.getTypeAction()) {
                        case EDIT:
                            viewModel.edit(detailUsed);
                            break;
                        case ADD:
                            viewModel.borrow(detailUsed);
                    }
                }
            }
        });
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
//        DetailUsed detailUsed = viewModel.getDetailUsedMutableLiveData().getValue();
//        detailUsed.setDateUsed(Calendar.getInstance().getTimeInMillis());
//        viewModel.getDetailUsedMutableLiveData().postValue(detailUsed);
    }
}
