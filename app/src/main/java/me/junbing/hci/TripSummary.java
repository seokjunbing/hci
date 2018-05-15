package me.junbing.hci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class TripSummary extends AppCompatActivity {
    private ListView listView;
    private BusAdapter mAdapter;
    ArrayList<Bus> buses = new ArrayList<>();

    private long selectPos = ListView.NO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip_summary);
        listView = findViewById(R.id.summary_lv);
        initializeData();
        initializeAdapter();
    }


    private void initializeData(){
        int choices[] = {1,4};
        Boolean priorities[] = {true, false};
        buses = Bus.getSelectBusArrayList("11/30/18", "Logan Airport", "BOS -> HAN", choices, priorities);
    }


    private void initializeAdapter(){
        mAdapter = new BusAdapter(this, buses, true);
        listView.setAdapter(mAdapter);

    }
}
