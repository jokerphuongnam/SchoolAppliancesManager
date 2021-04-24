package com.example.schoolappliancesmanager.ui.main.appliance;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentApplianceBinding;
import com.example.schoolappliancesmanager.ui.add.AddActivity;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_ACTION;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_UPDATE;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TypeUpdate.APPLIANCE;

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
            adapter = new ApplianceAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
            binding.list.setLayoutManager(layoutManager);
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
        viewModel.getData().observe(getViewLifecycleOwner(), appliances -> {
            getAdapter().submitList(appliances);
        });
        binding.addBtn.setOnClickListener((v) -> {
            Intent intent = getIntent();
            intent.removeExtra(TYPE_ACTION);
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
    }
}
