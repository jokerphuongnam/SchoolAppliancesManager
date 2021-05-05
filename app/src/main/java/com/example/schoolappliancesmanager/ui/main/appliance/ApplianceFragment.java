package com.example.schoolappliancesmanager.ui.main.appliance;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentApplianceBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.ui.add.AddActivity;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;
import com.example.schoolappliancesmanager.util.ItemClickRecycler;
import com.example.schoolappliancesmanager.util.Resource;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.example.schoolappliancesmanager.ui.add.AddActivity.TypeUpdate.APPLIANCE;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_UPDATE;

@AndroidEntryPoint
public class ApplianceFragment extends BaseFragment<FragmentApplianceBinding, ApplianceViewModel> {

    @Override
    public ApplianceViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(ApplianceViewModel.class);
    }

    public ApplianceFragment() {
        super(R.layout.fragment_appliance);
    }

    private ApplianceAdapter adapter = null;

    @NonNull
    private ApplianceAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ApplianceAdapter(new ItemClickRecycler<Appliance>() {
                @Override
                public void delete(Appliance item) {
                    viewModel.deleteAppliance(item);
                }

                @Override
                public void edit(Appliance item) {
                    Intent intent = getIntent();
                    intent.putExtra(DATA, item);
                    startActivity(intent);
                }
            });
        }
        return adapter;
    }

    private Intent intent = null;

    @NonNull
    private Intent getIntent() {
        if (intent == null) {
            intent =  new Intent(getContext(), AddActivity.class);
            intent.putExtra(TYPE_UPDATE, APPLIANCE.name());
        }
        return intent;
    }

    @Override
    public void createView() {
        binding.list.setAdapter(getAdapter());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);
        viewModel.getData().observe(getViewLifecycleOwner(), appliances -> {
            getAdapter().submitList(appliances);
        });
        viewModel.getSuccess().observe(getViewLifecycleOwner(), resource -> {
            if (resource instanceof Resource.Success) {
                showToast(getString(R.string.delete_complete));
            }
        });
        binding.addBtn.setOnClickListener((v) -> {
            Intent intent = getIntent();
            intent.removeExtra(DATA);
            startActivity(intent);
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
        viewModel.initData();
    }
}
