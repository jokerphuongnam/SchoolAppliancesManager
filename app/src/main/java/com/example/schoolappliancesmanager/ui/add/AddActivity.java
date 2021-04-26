package com.example.schoolappliancesmanager.ui.add;

import android.content.Intent;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.StringRes;
import androidx.appcompat.app.ActionBar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ActivityAddBinding;
import com.example.schoolappliancesmanager.ui.add.appliance.AddApplianceFragment;
import com.example.schoolappliancesmanager.ui.add.borrow.BorrowFragment;
import com.example.schoolappliancesmanager.ui.add.room.AddRoomFragment;
import com.example.schoolappliancesmanager.ui.base.BaseActivity;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.schoolappliancesmanager.ui.add.AddActivity.TypeAction.ADD;
import static com.example.schoolappliancesmanager.ui.add.AddActivity.TypeAction.EDIT;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;
import static com.example.schoolappliancesmanager.ui.main.MainActivity.TYPE_UPDATE;

@AndroidEntryPoint
public class AddActivity extends BaseActivity<ActivityAddBinding, AddViewModel> {

    public AddActivity() {
        super(R.layout.activity_add);
    }

    @Override
    public AddViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(AddViewModel.class);
    }

    private void setUpFragment() {
        switch (viewModel.getTypeUpdate()) {
            case DETAIL_USED:
                openFragment(new BorrowFragment(), getString(R.string.borrow));
                break;
            case ROOM:
                openFragment(new AddRoomFragment(), getString(R.string.add_room_fragment));
                break;
            case APPLIANCE:
                openFragment(new AddApplianceFragment(), getString(R.string.add_room_fragment));
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
                setTitle(
                        getIntent().hasExtra(DATA),
                        AddActivity.TypeUpdate.valueOf(getIntent().getStringExtra(TYPE_UPDATE))
                )
        );
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @NonNull
    private String setTitle(boolean isUpdate, @NonNull AddActivity.TypeUpdate typeUpdate) {
        return (isUpdate ? getString(R.string.update_action) : getString(R.string.add_action)) + " " + getString(typeUpdate.getRes());
    }

    @Override
    public void createView() {
        Intent intent = getIntent();
        viewModel.setTypeUpdate(AddActivity.TypeUpdate.valueOf(intent.getStringExtra(TYPE_UPDATE)));
        viewModel.setTypeAction(intent.hasExtra(DATA) ? EDIT : ADD);
        setUpFragment();
        setUpActionBar();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public enum TypeAction {
        EDIT, ADD
    }


    public enum TypeUpdate {
        DETAIL_USED(R.string.borrow), APPLIANCE(R.string.appliance), ROOM(R.string.room);

        TypeUpdate(int res) {
            this.res = res;
        }

        @StringRes
        private final int res;

        @StringRes
        public int getRes() {
            return res;
        }
    }
}
