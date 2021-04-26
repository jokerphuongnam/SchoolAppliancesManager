package com.example.schoolappliancesmanager.ui.main.room;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentRoomBinding;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.ui.add.AddActivity;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;
import com.example.schoolappliancesmanager.util.ItemClickRecycler;
import com.example.schoolappliancesmanager.util.Resource;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.example.schoolappliancesmanager.ui.add.AddActivity.TypeUpdate.ROOM;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_UPDATE;

@AndroidEntryPoint
public class RoomFragment extends BaseFragment<FragmentRoomBinding, RoomViewModel> {

    @Override
    public RoomViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(RoomViewModel.class);
    }

    public RoomFragment() {
        super(R.layout.fragment_room);
    }


    private RoomsAdapter adapter = null;

    @NonNull
    private RoomsAdapter getAdapter(){
        if(adapter == null){
            adapter = new RoomsAdapter(new ItemClickRecycler<Room>() {
                @Override
                public void delete(Room item) {
                    viewModel.deleteRoom(item);
                }

                @Override
                public void edit(Room item) {
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
            intent = new Intent(getContext(), AddActivity.class);
            intent.putExtra(TYPE_UPDATE, ROOM.name());
        }
        return intent;
    }

    @Override
    public void createView() {
        binding.list.setAdapter(getAdapter());
        viewModel.getData().observe(getViewLifecycleOwner(), rooms -> {
            getAdapter().submitList(rooms);
        });
        viewModel.getSuccess().observe(getViewLifecycleOwner(), resource -> {
            if (resource instanceof Resource.Success) {
                showToast(getString(R.string.delete_compelete));
            }
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);
        binding.addBtn.setOnClickListener((v) -> {
            Intent intent = getIntent();
            intent.removeExtra(DATA);
            startActivity(intent);
        });
    }

    public PublishSubject<Integer> getSelectPublisher() {
        if(selectPublisher == null){
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
