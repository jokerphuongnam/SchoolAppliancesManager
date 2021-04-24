package com.example.schoolappliancesmanager.ui.add;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ActivityAddBinding;
import com.example.schoolappliancesmanager.ui.add.appliance.AddApplianceFragment;
import com.example.schoolappliancesmanager.ui.add.room.AddRoomFragment;
import com.example.schoolappliancesmanager.ui.base.BaseActivity;
import com.example.schoolappliancesmanager.ui.main.MainActivity;

import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_UPDATE;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TypeUpdate.APPLIANCE;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TypeUpdate.ROOM;

public class AddActivity extends BaseActivity<ActivityAddBinding, AddViewModel> {

    public AddActivity() {
        super(R.layout.activity_add);
    }

    @Override
    public AddViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(AddViewModel.class);
    }

    private void setUpFragment() {
        Intent intent = getIntent();
        switch (MainActivity.TypeUpdate.valueOf(intent.getStringExtra(TYPE_UPDATE))) {
            case ROOM:
                openFragment(new AddRoomFragment(), getString(R.string.add_room_fragment));
                viewModel.setTypeUpdate(ROOM);
                break;
            case APPLIANCE:
                openFragment(new AddApplianceFragment(), getString(R.string.add_room_fragment));
                viewModel.setTypeUpdate(APPLIANCE);
        }
    }

    private <F extends Fragment> void openFragment(F fragment, String tag) {
        openFragment(R.id.add, fragment, tag);
    }

    private ActionBar actionBar;

    private void setUpActionBar() {
        setSupportActionBar(binding.toolBar);
        actionBar = getSupportActionBar();
        actionBar.setTitle(
                setTitle(getIntent().hasExtra(DATA),
                        MainActivity.TypeUpdate.valueOf(getIntent().getStringExtra(TYPE_UPDATE))
                )
        );
    }

    @NonNull
    private String setTitle(boolean isUpdate, MainActivity.TypeUpdate typeUpdate) {
        return (isUpdate ? getString(R.string.update_action) : getString(R.string.add_action)) + " "
                + (typeUpdate == APPLIANCE ? getString(R.string.appliance) : getString(R.string.room));
    }

    @Override
    public void createView() {
        setUpFragment();
        setUpActionBar();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public enum TypeAction {
        EDIT, ADD
    }
}
