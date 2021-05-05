package com.example.schoolappliancesmanager.ui.main;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ActivityMainBinding;
import com.example.schoolappliancesmanager.databinding.LayoutHeaderDrawerBinding;
import com.example.schoolappliancesmanager.ui.base.BaseActivity;
import com.example.schoolappliancesmanager.ui.main.appliance.ApplianceFragment;
import com.example.schoolappliancesmanager.ui.main.detailused.DetailUsedFragment;
import com.example.schoolappliancesmanager.ui.main.room.RoomFragment;
import com.example.schoolappliancesmanager.ui.main.statistical.StatisticalFragment;

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
        openFragment(getDetailUsedFragment(), getResources().getString(R.string.detail_used));
        drawerToggle = new ActionBarDrawerToggle(this, binding.activityMainDrawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.activityMainDrawer.addDrawerListener(drawerToggle);
        binding.navView.setNavigationItemSelectedListener((item) -> {
            switch (item.getItemId()) {
                case R.id.appliances:
                    openFragment(getApplianceFragment(), getResources().getString(R.string.appliances));
                    break;
                case R.id.detailUsed:
                    openFragment(getDetailUsedFragment(), getResources().getString(R.string.detail_used));
                    break;
                case R.id.rooms:
                    openFragment(getRoomFragment(), getResources().getString(R.string.rooms));
                    break;
                case R.id.appliance_statistical:
                    openFragment(getStatisticalFragment(), getString(R.string.appliance_statistical));
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
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                return drawerToggle.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    private DetailUsedFragment detailUsedFragment = null;
    private RoomFragment roomFragment = null;
    private ApplianceFragment applianceFragment = null;
    private StatisticalFragment statisticalFragment = null;
    private final CompositeDisposable composite = new CompositeDisposable();

    private void setSelectedMenu(int index) {
        binding.navView.getMenu().getItem(0).setChecked(index == 0);
        binding.navView.getMenu().getItem(1).setChecked(index == 1);
        binding.navView.getMenu().getItem(2).setChecked(index == 2);
        binding.navView.getMenu().getItem(3).setChecked(index == 3);
    }

    private DetailUsedFragment getDetailUsedFragment() {
        if (detailUsedFragment == null) {
            detailUsedFragment = new DetailUsedFragment();
            Disposable selectDisposable = detailUsedFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(0);
            });
            composite.add(selectDisposable);
        }
        return detailUsedFragment;
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

    private ApplianceFragment getApplianceFragment() {
        if (applianceFragment == null) {
            applianceFragment = new ApplianceFragment();
            Disposable subscribe = applianceFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(2);
            });
            composite.add(subscribe);
        }
        return applianceFragment;
    }

    public StatisticalFragment getStatisticalFragment() {
        if(statisticalFragment == null){
            statisticalFragment = new StatisticalFragment();
            Disposable subscribe = statisticalFragment.getSelectPublisher().subscribe(integer -> {
                setSelectedMenu(3);
            });
            composite.add(subscribe);
        }
        return statisticalFragment;
    }

    private long clickFirstTime = 0;

    protected void twiceTimeToExit() {
        if (clickFirstTime == 0L) {
            clickFirstTime = System.currentTimeMillis();
            showToast(getString(R.string.mess_when_click_back_btn));
        } else {
            if (System.currentTimeMillis() - clickFirstTime < 2000L) {
                finishAffinity();
            }
        }
    }

    @Override
    public void onBackPressed() {
        twiceTimeToExit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        composite.dispose();
    }

    public static final String TYPE_UPDATE = "TYPE_UPDATE";
    public static final String TYPE_ACTION = "TYPE_ACTION";
    public static final String DATA = "DATA";
}