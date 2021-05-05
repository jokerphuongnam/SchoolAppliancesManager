package com.example.schoolappliancesmanager.ui.add.borrow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.schoolappliancesmanager.databinding.ItemApplianceSpinnerBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;

import java.util.List;

public class ApplianceSpinnerAdapter extends ArrayAdapter<Appliance> {
    public ApplianceSpinnerAdapter(@NonNull Context context, List<Appliance> applianceList) {
        super(context, 0, applianceList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, parent);
    }

    @NonNull
    private View initView(int position, @NonNull ViewGroup parent){
        ItemApplianceSpinnerBinding binding = ItemApplianceSpinnerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        binding.setAppliance((Appliance) getItem(position));
        return binding.getRoot();
    }
}
