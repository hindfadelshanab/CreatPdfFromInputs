package com.example.creatpdffrominputs;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class LineChartActivity extends AppCompatActivity {

    LineChart lineChart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_chart);

        lineChart = findViewById(R.id.chart);
        LineDataSet lineDataSet = new LineDataSet(dataValues1(),"Data set 1");
        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(lineDataSet);

        LineData lineData = new LineData(dataSets);
        lineChart.setData(lineData);
        lineChart.invalidate();


    }

    private ArrayList<Entry> dataValues1(){

        ArrayList<Entry> dataValus = new ArrayList<Entry>();

        dataValus.add(new Entry(0,20));
        dataValus.add(new Entry(1,24));
        dataValus.add(new Entry(2,2));
        dataValus.add(new Entry(3,10));
        dataValus.add(new Entry(4,28));

        return dataValus;
    }
}