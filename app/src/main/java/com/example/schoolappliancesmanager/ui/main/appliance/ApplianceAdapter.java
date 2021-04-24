package com.example.schoolappliancesmanager.ui.main.appliance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolappliancesmanager.databinding.ItemApplianceBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;

public class ApplianceAdapter extends ListAdapter<Appliance, ApplianceAdapter.ViewHolder> {

    protected ApplianceAdapter() {
        super(getDiffCallback());
    }

    @NonNull
    @Override
    public ApplianceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplianceAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final ItemApplianceBinding binding;
        @NonNull
        private final Context context;

        public ViewHolder(@NonNull ItemApplianceBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        public void bind(Appliance appliance) {
            binding.setContext(context);
            binding.setAppliance(appliance);
        }

        @NonNull
        static ViewHolder create(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemApplianceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), parent.getContext());
        }
    }

    private static DiffUtil.ItemCallback<Appliance> diffCallback = null;

    private static DiffUtil.ItemCallback<Appliance> getDiffCallback() {
        if (diffCallback == null) {
            diffCallback = new DiffUtil.ItemCallback<Appliance>() {
                @Override
                public boolean areItemsTheSame(@NonNull Appliance oldItem, @NonNull Appliance newItem) {
                    return oldItem.getApplianceId() == newItem.getApplianceId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Appliance oldItem, @NonNull Appliance newItem) {
                    return oldItem.equals(newItem);
                }
            };
        }
        return diffCallback;
    }
}
