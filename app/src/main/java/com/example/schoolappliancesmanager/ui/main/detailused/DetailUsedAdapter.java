package com.example.schoolappliancesmanager.ui.main.detailused;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolappliancesmanager.databinding.ItemDetailUsedBinding;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;

public class DetailUsedAdapter extends ListAdapter<DetailUsed, DetailUsedAdapter.ViewHolder> {

    protected DetailUsedAdapter() {
        super(getDiffCallback());
    }

    @NonNull
    @Override
    public DetailUsedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailUsedAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private ItemDetailUsedBinding binding;

        public ViewHolder(@NonNull ItemDetailUsedBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(DetailUsed detailUsed) {
            binding.setDetailUsed(detailUsed);
        }

        @NonNull
        static ViewHolder create(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemDetailUsedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
        }
    }

    private static DiffUtil.ItemCallback<DetailUsed> diffCallback = null;

    private static DiffUtil.ItemCallback<DetailUsed> getDiffCallback() {
        if (diffCallback == null) {
            diffCallback = new DiffUtil.ItemCallback<DetailUsed>() {
                @Override
                public boolean areItemsTheSame(@NonNull DetailUsed oldItem, @NonNull DetailUsed newItem) {
                    return oldItem.getRoomName().equals(newItem.getRoomName()) && oldItem.getApplianceId() == newItem.getApplianceId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull DetailUsed oldItem, @NonNull DetailUsed newItem) {
                    return oldItem.equals(newItem);
                }
            };
        }
        return diffCallback;
    }
}
