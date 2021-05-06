package com.example.schoolappliancesmanager.ui.chart;

import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.lifecycle.ViewModelProvider;

import com.example.schoolappliancesmanager.R;
import com.example.schoolappliancesmanager.databinding.ActivityChartStatisticalBinding;
import com.example.schoolappliancesmanager.model.database.domain.supportquery.ApplianceStatisticalByMonthTuple;
import com.example.schoolappliancesmanager.ui.base.BaseActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;

import static com.example.schoolappliancesmanager.ui.main.statistical.StatisticalFragment.MONTH;
import static com.example.schoolappliancesmanager.ui.main.statistical.StatisticalFragment.YEAR;

@AndroidEntryPoint
public class StatisticalChartActivity extends BaseActivity<ActivityChartStatisticalBinding, StatisticalChartViewModel> {
    public StatisticalChartActivity() {
        super(R.layout.activity_chart_statistical);
    }

    private void initToolBar(int year, int month) {
        setSupportActionBar(binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getTitle(year, month));
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    private String getTitle(int year, int month) {
        return year != 0 ? getString(R.string.chart_title) + " " + getString(R.string.at) + " " + getString(R.string.month) + " " + month + " " + getString(R.string.year) + " " + year :
                getString(R.string.chart_title);
    }

    @Override
    public void createView() {
        viewModel.getData().observe(this, applianceStatisticalByMonthTuples -> {
            BarChart applianceChart = binding.applianceChart;
            BarData barData = new BarData();
            BarDataSet barDataSet = new BarDataSet(getDataChart(applianceStatisticalByMonthTuples), "");
            barData.addDataSet(barDataSet);
            List<String> names = getNames(applianceStatisticalByMonthTuples);
            ValueFormatter valueFormatter = new ValueFormatter() {
                @Override
                public String getAxisLabel(float value, AxisBase axis) {
                    return names.get((int) value - 1);
                }
            };
            barData.setBarWidth(0.9f);
            applianceChart.setFitBars(true);
            XAxis xAxis = applianceChart.getXAxis();
            xAxis.setGranularity(1f);
            xAxis.setValueFormatter(valueFormatter);
            YAxis axisLeft = applianceChart.getAxisLeft();
            YAxis axisRight = applianceChart.getAxisRight();
            axisLeft.setGranularity(1);
            axisRight.setGranularity(1);

            applianceChart.setData(barData);
            applianceChart.invalidate();
        });
    }

    private List<BarEntry> getDataChart(@NonNull List<ApplianceStatisticalByMonthTuple> applianceStatisticalByMonthTuples) {
        return applianceStatisticalByMonthTuples.stream().map((applianceStatisticalByMonthTuple) -> new BarEntry((float) applianceStatisticalByMonthTuple.getApplianceId(), (float) applianceStatisticalByMonthTuple.getQuantity())).collect(Collectors.toList());
    }

    private List<String> getNames(@NonNull List<ApplianceStatisticalByMonthTuple> applianceStatisticalByMonthTuples) {
        return applianceStatisticalByMonthTuples.stream().map(ApplianceStatisticalByMonthTuple::getApplianceName).collect(Collectors.toList());
    }

    @Override
    public StatisticalChartViewModel setUpViewModel() {
        return new ViewModelProvider(this).get(StatisticalChartViewModel.class);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        int year = getIntent().getIntExtra(YEAR, 0);
        int month = getIntent().getIntExtra(MONTH, 0);
        viewModel.setTime(year, month);
        viewModel.statisticalByMonth();
        initToolBar(year, month);
    }

    @Override
    protected void onPause() {
        super.onPause();
        viewModel.onCleared();
    }
}
