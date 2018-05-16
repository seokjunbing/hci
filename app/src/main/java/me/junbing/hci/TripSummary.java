package me.junbing.hci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

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

        findViewById(R.id.checkout_button).setOnClickListener(new HandleCheckOutClick());
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


            buses.add(Bus.getSelectBus(departureDate, busStopDepart, fromLoc,toLoc,
                    busChoiceInitial, busChoiceInitialPriority));

            int priority_premium = 0;

            if(busChoiceInitialPriority) {
                priority_premium += 10;
            }

            int base_ticket_price = 90;
            int tax = 10;

            if(isRoundTrip) {

                String returnDate = extras.getString("return_date");
                int busChoiceReturn = extras.getInt("bus_choice_return");
                Boolean busChoiceReturnPriority = extras.getBoolean("bus_choice_return_priority");

                String busStopReturn = extras.getString("bus_stop_return");

                buses.add(Bus.getSelectBus(returnDate, busStopReturn, toLoc, fromLoc,
                        busChoiceReturn, busChoiceReturnPriority));

                if(busChoiceReturnPriority) {
                    priority_premium += 10;
                }
                base_ticket_price += 50;
            }

            int total_persons = 0;
            total_persons = extras.getInt("adult_count")
                    + extras.getInt("children_count")
                    + extras.getInt("infant_count");

            ((TextView)findViewById(R.id.price_pp_val)).setText("$" + Integer.toString(base_ticket_price + priority_premium));
            ((TextView)findViewById(R.id.TF_pp_val)).setText("$" + Integer.toString(tax));
            ((TextView)findViewById(R.id.total_pp_val)).setText("$" + Integer.toString(base_ticket_price + priority_premium + tax));
            ((TextView)findViewById(R.id.total_price_val)).setText("$" + Integer.toString((base_ticket_price + priority_premium + tax) * total_persons));

            if (total_persons == 1) {
                ((TextView)findViewById(R.id.total_price)).setText("TOTAL (1 PASSENGER)");
            }
            else {
                ((TextView)findViewById(R.id.total_price)).setText("TOTAL (" + Integer.toString(total_persons) + " PASSENGERS)");

            }

        }

    }


    private void initializeAdapter(){
        mAdapter = new BusAdapter(this, buses, true);
        listView.setAdapter(mAdapter);
    }

    private class  HandleCheckOutClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Intent intent = new Intent(TripSummary.this, Payment.class);


                intent.putExtra("from", extras.getString("from"));
                intent.putExtra("to", extras.getString("to"));
                intent.putExtra("departure_date", extras.getString("departure_date"));
                intent.putExtra("return_date", extras.getString("return_date"));
                intent.putExtra("adult_count", extras.getInt("adult_count"));
                intent.putExtra("children_count", extras.getInt("children_count"));
                intent.putExtra("infant_count", extras.getInt("infant_count"));
                intent.putExtra("is_round_trip", extras.getBoolean("is_round_trip"));

                intent.putExtra("bus_stop_depart", extras.getString("bus_stop_depart"));
                intent.putExtra("bus_stop_return", extras.getString("bus_stop_return"));

                intent.putExtra("bus_choice_initial_priority", extras.getBoolean("bus_choice_initial_priority"));
                intent.putExtra("bus_choice_initial", extras.getInt("bus_choice_initial"));

                intent.putExtra("bus_choice_return_priority", extras.getBoolean("bus_choice_return_priority"));
                intent.putExtra("bus_choice_return", extras.getInt("bus_choice_return"));

                startActivity(intent);
            }
        }
    }


}
