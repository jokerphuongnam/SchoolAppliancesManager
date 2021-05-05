package com.example.schoolappliancesmanager.ui.add.borrow;

import android.app.DatePickerDialog;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentBorrowBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.ui.add.AddViewModel;
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

    private AddViewModel activityViewModel;

    private void setUpActivityViewModel() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
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

                DetailUsed detailUsed = viewModel.getDetailUsed();
                detailUsed.setDateUsed(calendar.getTimeInMillis());
                binding.setDetailUsed(detailUsed);
            };
        }
        return datePickerCallBack;
    }

    public ArrayAdapter<Appliance> getApplianceAdapter() {
        List<Appliance> appliances;
        if (viewModel.getAppliances().getValue() == null || viewModel.getAppliances().getValue().isEmpty()) {
            appliances = new ArrayList<>();
        } else {
            appliances = viewModel.getAppliances().getValue();
        }
        return new ApplianceSpinnerAdapter(getContext(), appliances);
    }

    public ArrayAdapter<String> getRoomAdapter() {
        List<String> rooms;
        if (viewModel.getRooms().getValue() == null || viewModel.getRooms().getValue().isEmpty()) {
            rooms = new ArrayList<>();
        } else {
            rooms = viewModel.getRooms().getValue().stream().map(Room::getRoomName).collect(Collectors.toList());
        }
        return new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, rooms);
    }

    private void initAdapterSpinner() {
        binding.applianceSpinner.setAdapter(getApplianceAdapter());
        binding.roomSpinner.setAdapter(getRoomAdapter());
    }

    private void initData() {
        viewModel.initData((DetailUsed) getActivity().getIntent().getSerializableExtra(DATA));
        binding.setDetailUsed(viewModel.getDetailUsed());
        binding.roomSpinner.setEnabled(!getActivity().getIntent().hasExtra(DATA));
        binding.applianceSpinner.setEnabled(!getActivity().getIntent().hasExtra(DATA));
    }

    @Override
    public void createView() {
        initAdapterSpinner();
        setUpActivityViewModel();
        binding.calendarChoose.setOnClickListener((v -> {
            getDatePicker().show();
        }));
        initData();
        viewModel.initApplianceAndRoomName();
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
                getActivity().finish();
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
                    switch (activityViewModel.getTypeAction()) {
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