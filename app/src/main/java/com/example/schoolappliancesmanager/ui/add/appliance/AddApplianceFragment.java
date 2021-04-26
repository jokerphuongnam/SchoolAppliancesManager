package com.example.schoolappliancesmanager.ui.add.appliance;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentAddApplianceBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.ui.add.AddViewModel;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;

@AndroidEntryPoint
public class AddApplianceFragment extends BaseFragment<FragmentAddApplianceBinding, AddApplianceViewModel> {
    public AddApplianceFragment() {
        super(R.layout.fragment_add_appliance);
    }

    @Override
    public AddApplianceViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(AddApplianceViewModel.class);
    }

    private AddViewModel activityViewModel;

    private void setUpActivityViewModel() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
    }

    private final ActivityResultLauncher<Intent> imageChoose = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), (result) -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            binding.imageLayout.setDisplayedChild(1);
        }
    });

    @Override
    public void createView() {
        setUpActivityViewModel();
        binding.imageLayout.setMeasureAllChildren(false);
        viewModel.initAppliance((Appliance) getActivity().getIntent().getSerializableExtra(DATA));
        viewModel.getSuccess().observe(getViewLifecycleOwner(), (success) -> {
            getActivity().finish();
        });
        binding.setAppliance(viewModel.getAppliance());
        binding.success.setOnClickListener((v) -> {
            if (binding.getAppliance().getApplianceName().isEmpty()) {
                binding.applianceNameError.setVisibility(View.VISIBLE);
            } else {
                binding.applianceNameError.setVisibility(View.GONE);
                Appliance appliance = binding.getAppliance();
                int index = binding.spinner.getSelectedItemPosition();
                appliance.setStatus(Appliance.Status.values()[index]);
                switch (activityViewModel.getTypeAction()) {
                    case EDIT:
                        viewModel.editAppliance(appliance);
                        break;
                    case ADD:
                        viewModel.addAppliance(appliance);
                        break;
                }
            }
        });
        binding.addImage.setOnClickListener((v) -> {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.setType("image/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            imageChoose.launch(intent);
        });
        binding.openCamera.setOnClickListener((v) -> {

        });
        binding.deleteImage.setOnClickListener((v) -> {
            binding.imageLayout.setDisplayedChild(0);
        });
    }
}
