package com.example.schoolappliancesmanager.ui.main.statistical;

import android.content.Intent;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.FragmentStatisticalBinding;
import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;
import com.example.schoolappliancesmanager.ui.base.BaseFragment;
import com.example.schoolappliancesmanager.ui.chart.StatisticalChartActivity;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import java.util.ArrayList;
import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.subjects.PublishSubject;

import static com.example.schoolappliancesmanager.ui.main.MainActivity.DATA;

@AndroidEntryPoint
public class StatisticalFragment extends BaseFragment<FragmentStatisticalBinding, StatisticalViewModel> {
    public StatisticalFragment() {
        super(R.layout.fragment_statistical);
    }

    private StatisticalAdapter adapter = null;

    public StatisticalAdapter getAdapter() {
        if (adapter == null) {
            adapter = new StatisticalAdapter();
        }
        return adapter;
    }

    private MonthPickerDialog monthPickerDialog = null;

    public MonthPickerDialog getMonthPickerDialog() {
        if (monthPickerDialog == null) {
            Calendar today = Calendar.getInstance();
            MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(requireContext(), (selectedMonth, selectedYear) -> {
                viewModel.setTime(selectedYear, selectedMonth);
                viewModel.statisticalByMonth();
            }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));
            builder.setTitle(getString(R.string.choose_month_statistical));
            monthPickerDialog = builder.build();
        }
        return monthPickerDialog;
    }

    @Override
    public void createView() {
        viewModel.getData().observe(getViewLifecycleOwner(), appliancesStatisticalByMonthTuples -> {
            getAdapter().submitList(appliancesStatisticalByMonthTuples);
        });
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.list.setLayoutManager(layoutManager);
        binding.list.setAdapter(getAdapter());
        binding.calendarChoose.setOnClickListener((v) -> {
            getMonthPickerDialog().show();
        });
        binding.chartOpen.setOnClickListener((v) -> {
            if (!viewModel.getData().getValue().isEmpty()) {
                Intent intent = new Intent(requireContext(), StatisticalChartActivity.class);
                intent.putExtra(DATA, viewModel.getMonth());
                intent.putExtra(DATA, viewModel.getYear());
                startActivity(intent);
            }else {
                showToast(getString(R.string.statistical_be_empty));
            }
        });
    }

    @Override
    public StatisticalViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(StatisticalViewModel.class);
    }

    public PublishSubject<Integer> getSelectPublisher() {
        if (selectPublisher == null) {
            selectPublisher = PublishSubject.create();
        }
        return selectPublisher;
    }

    private PublishSubject<Integer> selectPublisher;

    @Override
    public void onResume() {
        super.onResume();
        getSelectPublisher().onNext(0);
        viewModel.setTime(null, null);
        viewModel.statisticalByMonth();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewModel.onCleared();
    }

    public static String MONTH = "MONTH";
    public static String YEAR = "YEAR";
}
