package com.example.schoolappliancesmanager.ui.main.room;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentRoomBinding;
import com.example.schoolappliancesmanager.ui.add.AddActivity;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_ACTION;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_UPDATE;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TypeUpdate.ROOM;

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
            adapter = new RoomsAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
            binding.list.setLayoutManager(layoutManager);
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
        binding.addBtn.setOnClickListener((v)->{
            Intent intent = getIntent();
            intent.removeExtra(TYPE_ACTION);
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
