package com.example.schoolappliancesmanager.ui.add.room;

import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentAddRoomBinding;
import com.example.schoolappliancesmanager.model.database.domain.Room;
import com.example.schoolappliancesmanager.ui.add.AddViewModel;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;
import com.example.schoolappliancesmanager.util.Resource;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.schoolappliancesmanager.ui.add.AddActivity.TypeAction.EDIT;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;

@AndroidEntryPoint
public class AddRoomFragment extends BaseFragment<FragmentAddRoomBinding, AddRoomViewModel> {
    public AddRoomFragment() {
        super(R.layout.fragment_add_room);
    }

    @Override
    public AddRoomViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(AddRoomViewModel.class);
    }

    private void setUpActivityLayout() {
        activityViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
    }

    @Override
    public void createView() {
        setUpActivityLayout();
        viewModel.initRoom(getActivity().getIntent().getExtras().getParcelable(DATA));
        viewModel.getCheckRoomName().observe(getViewLifecycleOwner(), (resource) -> {
            if (resource instanceof Resource.Loading) {
                binding.roomNameError.setVisibility(View.GONE);
            } else if (resource instanceof Resource.Success) {
                viewModel.addRoom(binding.getRoom());
            } else if (resource instanceof Resource.Error) {
                binding.roomNameError.setText(R.string.room_name_same);
                binding.roomNameError.setVisibility(View.VISIBLE);
            }
        });
        viewModel.getSuccess().observe(getViewLifecycleOwner(), (success) -> {
            getActivity().finish();
        });
        binding.roomName.setEnabled(activityViewModel.getTypeAction() != EDIT);
        binding.setRoom(viewModel.getRoom());
        binding.success.setOnClickListener((v -> {
            Room room = binding.getRoom();
            int index = binding.spinner.getSelectedItemPosition();
            room.setType(Room.RoomType.values()[index]);
            switch (activityViewModel.getTypeAction()) {
                case EDIT:
                    viewModel.editRoom(room);
                    break;
                case ADD:
                    if (binding.getRoom().getRoomName().isEmpty()) {
                        binding.roomNameError.setVisibility(View.VISIBLE);
                        binding.roomNameError.setText(R.string.room_name_empty);
                    } else {
                        viewModel.checkRoom(room);
                    }
                    break;
            }
        }));
    }


    private AddViewModel activityViewModel;
}
