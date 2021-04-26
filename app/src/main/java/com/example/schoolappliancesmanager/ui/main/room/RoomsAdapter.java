package com.example.schoolappliancesmanager.ui.main.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ItemRoomBinding;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.util.ItemClickRecycler;

public class RoomsAdapter extends ListAdapter<Room, RoomsAdapter.ViewHolder> {

    private final ItemClickRecycler<Room> itemClickRecycler;

    protected RoomsAdapter(ItemClickRecycler<Room> itemClickRecycler) {
        super(getDiffCallback());
        this.itemClickRecycler = itemClickRecycler;
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType, itemClickRecycler);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    protected static class ViewHolder extends RecyclerView.ViewHolder {

        @NonNull
        private final ItemRoomBinding binding;
        @NonNull
        private final Context context;
        private final ItemClickRecycler<Room> itemClickRecycler;

        public ViewHolder(@NonNull ItemRoomBinding binding, ItemClickRecycler<Room> itemClickRecycler, @NonNull Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.itemClickRecycler = itemClickRecycler;
            this.context = context;
        }

        public void bind(Room room) {
            binding.setContext(context);
            binding.setRoom(room);

            binding.container.setOnClickListener((v) -> {
                itemClickRecycler.edit(room);
            });
            binding.container.setOnLongClickListener((v)->{
                PopupMenu popupMenu = new PopupMenu( context, v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_option_item, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener((menu)->{
                    switch (menu.getItemId()){
                        case R.id.edit:
                            itemClickRecycler.edit(room);
                            break;
                        case R.id.delete:
                            itemClickRecycler.delete(room);
                    }
                    return true;
                });
                popupMenu.show();
                return true;
            });
        }

        @NonNull
        static ViewHolder create(@NonNull ViewGroup parent, int viewType, ItemClickRecycler<Room> itemClickRecycler) {
            return new ViewHolder(ItemRoomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), itemClickRecycler, parent.getContext());
        }
    }

    private static DiffUtil.ItemCallback<Room> diffCallback = null;

    private static DiffUtil.ItemCallback<Room> getDiffCallback() {
        if (diffCallback == null) {
            diffCallback = new DiffUtil.ItemCallback<Room>() {
                @Override
                public boolean areItemsTheSame(@NonNull Room oldItem, @NonNull Room newItem) {
                    return oldItem.getRoomName().equals(newItem.getRoomName());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Room oldItem, @NonNull Room newItem) {
                    return oldItem.equals(newItem);
                }
            };
        }
        return diffCallback;
    }
}
