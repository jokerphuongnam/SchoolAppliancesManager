package com.example.schoolappliancesmanager.ui.main.detailused;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ItemDetailUsedBinding;
import com.example.schoolappliancesmanager.model.database.domain.DetailUsed;
import com.example.schoolappliancesmanager.util.ItemClickRecycler;

public class DetailUsedAdapter extends ListAdapter<DetailUsed, DetailUsedAdapter.ViewHolder> {

    private final ItemClickRecycler<DetailUsed> itemClickRecycler;

    public DetailUsedAdapter(ItemClickRecycler<DetailUsed> itemClickRecycler) {
        super(getDiffCallback());
        this.itemClickRecycler = itemClickRecycler;
    }

    @NonNull
    @Override
    public DetailUsedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType, itemClickRecycler);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailUsedAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final ItemDetailUsedBinding binding;
        private final ItemClickRecycler<DetailUsed> itemClickRecycler;
        private final Context context;

        public ViewHolder(@NonNull ItemDetailUsedBinding binding, ItemClickRecycler<DetailUsed> itemClickRecycler, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemClickRecycler = itemClickRecycler;
            this.context = context;
        }

        public void bind(DetailUsed detailUsed) {
            binding.setDetailUsed(detailUsed);

            binding.container.setOnClickListener((v) -> {
                itemClickRecycler.edit(detailUsed);
            });
            binding.container.setOnLongClickListener((v)->{
                PopupMenu popupMenu = new PopupMenu( context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_option_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener((menu)->{
                    switch (menu.getItemId()){
                        case R.id.edit:
                            itemClickRecycler.edit(detailUsed);
                        break;
                        case R.id.delete:
                            itemClickRecycler.delete(detailUsed);
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            });
        }

        @NonNull
        static ViewHolder create(@NonNull ViewGroup parent, int viewType, ItemClickRecycler<DetailUsed> itemClickRecycler) {
            return new ViewHolder(ItemDetailUsedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), itemClickRecycler, parent.getContext());
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
