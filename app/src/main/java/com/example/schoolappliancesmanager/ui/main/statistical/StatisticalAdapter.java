package com.example.schoolappliancesmanager.ui.main.statistical;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolappliancesmanager.databinding.ItemApplianceStatisticalBinding;
import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;

public class StatisticalAdapter extends ListAdapter<ApplianceStatisticalByMonthTuple, StatisticalAdapter.ViewHolder> {

    public StatisticalAdapter(){
        super(getDiffCallback());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType);
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemApplianceStatisticalBinding binding;

        public ViewHolder(@NonNull ItemApplianceStatisticalBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(ApplianceStatisticalByMonthTuple applianceStatisticalByMonthTuple){
            binding.setApplianceStatistical(applianceStatisticalByMonthTuple);
        }

        @NonNull
        static  ViewHolder create(@NonNull ViewGroup parent, int viewType){
            return new ViewHolder(ItemApplianceStatisticalBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    private static DiffUtil.ItemCallback<ApplianceStatisticalByMonthTuple> diffCallback = null;

    private static DiffUtil.ItemCallback<ApplianceStatisticalByMonthTuple> getDiffCallback() {
        if (diffCallback == null) {
            diffCallback = new DiffUtil.ItemCallback<ApplianceStatisticalByMonthTuple>() {
                @Override
                public boolean areItemsTheSame(@NonNull ApplianceStatisticalByMonthTuple oldItem, @NonNull ApplianceStatisticalByMonthTuple newItem) {
                    return oldItem.equals(newItem);
                }

                @Override
                public boolean areContentsTheSame(@NonNull ApplianceStatisticalByMonthTuple oldItem, @NonNull ApplianceStatisticalByMonthTuple newItem) {
                    return oldItem.equals(newItem);
                }
            };
        }
        return diffCallback;
    }
}
