package com.example.schoolappliancesmanager.ui.add.appliance;

import android.widget.ArrayAdapter;

import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentAddApplianceBinding;
import com.example.schoolappliancesmanager.ui.add.AddViewModel;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

public class AddApplianceFragment extends BaseFragment<FragmentAddApplianceBinding, AddApplianceViewModel> {
    public AddApplianceFragment() {
        super(R.layout.fragment_add_appliance);
    }

    @Override
    public AddApplianceViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(AddApplianceViewModel.class);
    }

    private void setUpActivityViewModel() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
    }


    @Override
    public void createView() {
        setUpActivityViewModel();
    }

    private AddViewModel activityViewModel;
}
