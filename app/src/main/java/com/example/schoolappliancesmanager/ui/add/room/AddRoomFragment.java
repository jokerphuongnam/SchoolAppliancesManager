package com.example.schoolappliancesmanager.ui.add.room;

import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentAddRoomBinding;
import com.example.schoolappliancesmanager.ui.add.AddActivity;
import com.example.schoolappliancesmanager.ui.add.AddViewModel;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;

public class AddRoomFragment extends BaseFragment<FragmentAddRoomBinding, AddRoomViewModel> {
    public AddRoomFragment() {
        super(R.layout.fragment_add_room);
    }

    @Override
    public AddRoomViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(AddRoomViewModel.class);
    }

    private void setUp(){
        activityViewModel = new ViewModelProvider(requireActivity()).get(AddViewModel.class);
    }

    @Override
    public void createView() {

    }

    private AddViewModel activityViewModel;
}
