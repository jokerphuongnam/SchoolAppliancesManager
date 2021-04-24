package com.example.schoolappliancesmanager.ui.main;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ActivityMainBinding;
import com.example.schoolappliancesmanager.databinding.LayoutHeaderDrawerBinding;
import com.example.schoolappliancesmanager.ui.base.BaseActivity;
import com.example.schoolappliancesmanager.ui.main.appliance.ApplianceFragment;
import com.example.schoolappliancesmanager.ui.main.borrow.BorrowFragment;
import com.example.schoolappliancesmanager.ui.main.detailused.DetailUsedFragment;
import com.example.schoolappliancesmanager.ui.main.room.RoomFragment;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> {

    private ActionBar actionBar;
    private ActionBarDrawerToggle drawerToggle;

    public MainActivity() {
        super(R.layout.activity_main);
    }

    @Override
    public MainViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(MainViewModel.class);
    }

    private LayoutHeaderDrawerBinding headerDrawerBinding;

    private void backButton() {
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Drawable drawable = getResources().getDrawable(R.drawable.ic_menu);
//        drawable.setColorFilter(getResources().getColor(R.color.white) , PorterDuff.Mode.SRC_ATOP);
        actionBar.setHomeAsUpIndicator(drawable);
        headerDrawerBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.layout_header_drawer, binding.navView, false);
    }

    @Override
    public void createView() {
        setSupportActionBar(binding.toolBar);
        actionBar = getSupportActionBar();
        assert actionBar != null;
        backButton();
        openFragment(getBorrowFragment(), getResources().getString(R.string.borrow));
        drawerToggle = new ActionBarDrawerToggle(this, binding.activityMainDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.activityMainDrawer.addDrawerListener(drawerToggle);
        binding.navView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.borrow:
                    openFragment(getBorrowFragment(), getResources().getString(R.string.borrow));
                    break;
                case R.id.rooms:
                    openFragment(getRoomFragment(), getResources().getString(R.string.rooms));
                    break;
                case R.id.detailUsed:
                    openFragment(getDetailUsedFragment(), getResources().getString(R.string.detail_used));
                    break;
                case R.id.appliances:
                    openFragment(getApplianceFragment(), getResources().getString(R.string.appliances));
                    break;
                case R.id.exit:
                    finishAffinity();
                    break;
            }
            binding.activityMainDrawer.closeDrawer(binding.navView);
            return drawerToggle.onOptionsItemSelected(item);
        });
    }

    private <F extends Fragment> void openFragment(F fragment, String tag) {
        actionBar.setTitle(tag);
        openFragment(R.id.main, fragment, tag);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return drawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private BorrowFragment borrowFragment = null;
    private RoomFragment roomFragment = null;
    private DetailUsedFragment detailUsedFragment = null;
    private ApplianceFragment applianceFragment = null;
    private final CompositeDisposable composite = new CompositeDisposable();

    private void setSelectedMenu(int index) {
        binding.navView.getMenu().getItem(0).setChecked(index == 0);
        binding.navView.getMenu().getItem(1).setChecked(index == 1);
        binding.navView.getMenu().getItem(2).setChecked(index == 2);
        binding.navView.getMenu().getItem(3).setChecked(index == 3);
    }

    private BorrowFragment getBorrowFragment() {
        if (borrowFragment == null) {
            borrowFragment = new BorrowFragment();
            Disposable subscribe = borrowFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(0);
            });
            composite.add(subscribe);
        }
        return borrowFragment;
    }

    private RoomFragment getRoomFragment() {
        if (roomFragment == null) {
            roomFragment = new RoomFragment();
            Disposable subscribe = roomFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(1);
            });
            composite.add(subscribe);
        }
        return roomFragment;
    }

    private DetailUsedFragment getDetailUsedFragment() {
        if (detailUsedFragment == null) {
            detailUsedFragment = new DetailUsedFragment();
            Disposable selectDisposable = detailUsedFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(2);
            });
            composite.add(selectDisposable);
            Disposable addClickDisposable = detailUsedFragment.getAddPublisher().subscribe(integer -> {
                openFragment(getBorrowFragment(), getString(R.string.borrow));
            });
            composite.add(addClickDisposable);
        }
        return detailUsedFragment;
    }

    private ApplianceFragment getApplianceFragment() {
        if (applianceFragment == null) {
            applianceFragment = new ApplianceFragment();
            Disposable subscribe = applianceFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(3);
            });
            composite.add(subscribe);
        }
        return applianceFragment;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        composite.dispose();
    }

    public static final String TYPE_UPDATE = "TYPE_UPDATE";
    public static final String TYPE_ACTION = "TYPE_ACTION";
    public static final String DATA = "DATA";

    public enum TypeUpdate {
        APPLIANCE, ROOM
    }
}