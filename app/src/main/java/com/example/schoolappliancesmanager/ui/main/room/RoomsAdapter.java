package com.example.schoolappliancesmanager.ui.main.room;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.schoolappliancesmanager.databinding.ItemRoomBinding;
import com.example.schoolappliancesmanager.model.database.domain.Room;

public class RoomsAdapter extends ListAdapter<Room, RoomsAdapter.ViewHolder> {

    protected RoomsAdapter() {
        super(getDiffCallback());
    }

    @NonNull
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return ViewHolder.create(parent, viewType);
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

        public ViewHolder(@NonNull ItemRoomBinding binding, Context context) {
            super(binding.getRoot());
            this.binding = binding;
            this.context = context;
        }

        public void bind(Room room) {
            binding.setContext(context);
            binding.setRoom(room);
        }

        @NonNull
        static ViewHolder create(@NonNull ViewGroup parent, int viewType) {
            return new ViewHolder(ItemRoomBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false), parent.getContext());
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
