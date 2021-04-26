package com.example.schoolappliancesmanager.ui.main.appliance;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ItemApplianceBinding;
import com.example.schoolappliancesmanager.model.database.domain.Appliance;
import com.example.schoolappliancesmanager.util.ItemClickRecycler;

public class ApplianceAdapter extends ListAdapter<Appliance, ApplianceAdapter.ViewHolder> {

    private final ItemClickRecycler<Appliance> itemClickRecycler;

    protected ApplianceAdapter(ItemClickRecycler<Appliance> itemClickRecycler) {
        super(getDiffCallback());
        this.itemClickRecycler = itemClickRecycler;
    }

    @NonNull
    @Override
    public ApplianceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType, itemClickRecycler);
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
        private final ItemClickRecycler<Appliance> itemClickRecycler;

        public ViewHolder(@NonNull ItemApplianceBinding binding, ItemClickRecycler<Appliance> itemClickRecycler, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemClickRecycler = itemClickRecycler;
            this.context = context;
        }

        public void bind(Appliance appliance) {
            binding.setContext(context);
            binding.setAppliance(appliance);

            binding.container.setOnClickListener((v) -> {
                itemClickRecycler.edit(appliance);
            });
            binding.container.setOnLongClickListener((v) -> {
                PopupMenu popupMenu = new PopupMenu(context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_option_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener((menu) -> {
                    switch (menu.getItemId()) {
                        case R.id.edit:
                            itemClickRecycler.edit(appliance);
                            break;
                        case R.id.delete:
                            itemClickRecycler.delete(appliance);
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            });
        }

        @NonNull
        static ViewHolder create(@NonNull ViewGroup parent, int viewType, ItemClickRecycler<Appliance> itemClickRecycler) {
            return new ViewHolder(ItemApplianceBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), itemClickRecycler, parent.getContext());
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
