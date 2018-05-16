package me.junbing.hci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class Confirmation extends AppCompatActivity {
    private ListView outbound_listView;
    private ListView return_listView;

    private BusAdapter outbound_mAdapter;
    private BusAdapter return_mAdapter;

    ArrayList<Bus> outbound_buses = new ArrayList<>();
    ArrayList<Bus> return_buses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        outbound_listView = findViewById(R.id.confirmation_outbound_lv);
        return_listView = findViewById(R.id.confirmation_return_lv);

        initializeData();
        initializeAdapters();
    }


    private void initializeData(){
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fromLoc = extras.getString("from");
            String toLoc = extras.getString("to");
            String departureDate = extras.getString("departure_date");
            Boolean isRoundTrip = extras.getBoolean("is_round_trip");
            int busChoiceInitial = extras.getInt("bus_choice_initial");
            Boolean busChoiceInitialPriority = extras.getBoolean("bus_choice_initial_priority");
            String busStopDepart = extras.getString("bus_stop_depart");


            outbound_buses.add(Bus.getSelectBus(departureDate, busStopDepart, fromLoc, toLoc,
                    busChoiceInitial, busChoiceInitialPriority));

            if(isRoundTrip) {
                String returnDate = extras.getString("return_date");
                int busChoiceReturn = extras.getInt("bus_choice_return");
                Boolean busChoiceReturnPriority = extras.getBoolean("bus_choice_return_priority");

                String busStopReturn = extras.getString("bus_stop_return");

                return_buses.add(Bus.getSelectBus(returnDate, busStopReturn, toLoc, fromLoc,
                        busChoiceReturn, busChoiceReturnPriority));
            }
            else {
                findViewById(R.id.return_trip).setVisibility(View.INVISIBLE);
                findViewById(R.id.confirmation_return_lv).setVisibility(View.INVISIBLE);

            }

            String greeting = "You are going to ";
            ((TextView)findViewById(R.id.confirmation_greeting)).setText(greeting + toLoc + "!");
        }




    }

    private void initializeAdapters(){
        outbound_mAdapter = new BusAdapter(this, outbound_buses, true);
        outbound_listView.setAdapter(outbound_mAdapter);

        return_mAdapter = new BusAdapter(this, return_buses, true);
        return_listView.setAdapter(return_mAdapter);
    }


}
