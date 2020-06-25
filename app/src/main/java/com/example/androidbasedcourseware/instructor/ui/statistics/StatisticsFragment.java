package com.example.androidbasedcourseware.instructor.ui.statistics;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.androidbasedcourseware.R;
import com.example.androidbasedcourseware.datalayer.DataAccessLayer;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {
    private BarChart turbocharger;
    private BarChart ballbearing;
    private RelativeLayout relativeLayout;
    private Spinner spinner;

    private float turboChargerFailed, turboChargerPassed;
    private float ballBearingFailed, ballBearingPassed;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        getStatistics();
        View root = inflater.inflate(R.layout.fragment_statistics, container, false);
        turbocharger = root.findViewById(R.id.turbocharger);
        ballbearing = root.findViewById(R.id.ballbearing);
        relativeLayout = root.findViewById(R.id.relativeLayout);

        List<String> category = new ArrayList<String>();
        category.add("CHART GRAPH");
        category.add("STUDENTS");
        category.add("PASSED");
        category.add("FAILED");

        spinner = root.findViewById(R.id.statistics_spinner);
        spinner.setVisibility(View.GONE);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, category);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);

        BarData turboData = new BarData(getXAxisValuesTurbo(), getDataSetTurbo());
        turbocharger.setData(turboData);
        turbocharger.animateXY(900, 900);
        turbocharger.invalidate();

        BarData ballData = new BarData(getXAxisValuesBallBearing(), getDataSetBallBearing());
        ballbearing.setData(ballData);
        ballbearing.animateXY(900, 900);
        ballbearing.invalidate();

        return root;
    }

    private void getStatistics() {
        DataAccessLayer da = new DataAccessLayer(getContext());
        int bbPassed = da.getCountBallBearingQuizPassed();
        int bbFailed = da.getCountBallBearingQuizFailed();
        int turboPassed = da.getCountTurbochargerQuizPassed();
        int turboFailed = da.getCountTurbochargerQuizFailed();

        turboChargerPassed = (float)turboPassed;
        turboChargerFailed = (float)turboFailed;
        ballBearingPassed = (float)bbPassed;
        ballBearingFailed = (float)bbFailed;
    }

    private ArrayList<IBarDataSet> getDataSetBallBearing() {
        ArrayList dataSets = null;

        ArrayList valueSet1 = new ArrayList();
        BarEntry v1e1 = new BarEntry(ballBearingPassed, 0); // Jan
        valueSet1.add(v1e1);

        ArrayList valueSet2 = new ArrayList();
        BarEntry v2e1 = new BarEntry(ballBearingFailed, 0); // Jan
        valueSet2.add(v2e1);

        BarDataSet barDataSet1 = new BarDataSet(valueSet1, "PASSED");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet2, "FAILED");
        barDataSet2.setColor(Color.rgb(155, 0, 0));

        dataSets = new ArrayList();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValuesBallBearing() {
        ArrayList xAxis = new ArrayList();
        xAxis.add("BALL BEARING");
        return xAxis;
    }

    private ArrayList<IBarDataSet> getDataSetTurbo() {
        ArrayList dataSets = null;

        ArrayList valueSet1 = new ArrayList();
        BarEntry v1e1 = new BarEntry(0.00f, 0); // Jan
        valueSet1.add(v1e1);

        ArrayList valueSet2 = new ArrayList();
        BarEntry v2e1 = new BarEntry(turboChargerPassed, 0); // Jan
        valueSet2.add(v2e1);

        BarDataSet barDataSet1 = new BarDataSet(valueSet2, "PASSED");
        barDataSet1.setColor(Color.rgb(0, 155, 0));
        BarDataSet barDataSet2 = new BarDataSet(valueSet1, "FAILED");
        barDataSet2.setColor(Color.rgb(155, 0, 0));

        dataSets = new ArrayList();
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);
        return dataSets;
    }

    private ArrayList<String> getXAxisValuesTurbo() {
        ArrayList xAxis = new ArrayList();
        xAxis.add("TURBOCHARGER");
        return xAxis;
    }
}