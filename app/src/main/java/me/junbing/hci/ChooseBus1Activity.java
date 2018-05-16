package me.junbing.hci;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

// referred https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd

public class ChooseBus1Activity extends AppCompatActivity {

    private ListView listView;
    private BusAdapter mAdapter;
    ArrayList<Bus> buses = new ArrayList<>();

    private long selectPos = ListView.NO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_bus1);
        listView = (ListView) findViewById(R.id.choose_bus_lv);
        initializeData();
        initializeAdapter();

        // disable it so that the user can't click on it before selecting a bus card
        ((Button) findViewById(R.id.select_bus)).setEnabled(false);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
                selectPos = id;

                view.findViewById(R.id.radioButton1).setClickable(true);
                view.findViewById(R.id.radioButton2).setClickable(true);

                Toast.makeText(ChooseBus1Activity.this, "pos: " + Long.toString(id), Toast.LENGTH_SHORT).show();

                System.out.println("child count: " + Integer.toString(listView.getChildCount()));
                for (int i = 0; i < listView.getChildCount(); i++) {
                    if ((int) id != i) {
                        View v = listView.getChildAt(i);
                        if (v != null) {
                            v.findViewById(R.id.radioButton1).setClickable(false);
                            v.findViewById(R.id.radioButton2).setClickable(false);
                        }
                    }
                }

                // enable the button when the user clicks on a bus card
                ((Button) findViewById(R.id.select_bus)).setEnabled(true);

            }
        });

        findViewById(R.id.select_bus).setOnClickListener(new HandleSearchClick());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int way = extras.getInt("way");

            if (way == 10) {
                ((TextView) findViewById(R.id.out_or_in)).setText("Choose your outbound trip");
            } else {
                ((TextView) findViewById(R.id.out_or_in)).setText("Choose your return trip");
            }

        }
    }

    private class HandleSearchClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Toast.makeText(ChooseBus1Activity.this, "pressed search", Toast.LENGTH_SHORT).show();
            Intent intent;

            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Boolean isRoundTrip = extras.getBoolean("is_round_trip");
                Boolean priority = buses.get((int) selectPos).priority;

                int way = extras.getInt("way");

                // currently selecting initial trip of a round trip
                if (isRoundTrip && way == 10) {
                    // next selection is the return trip bus
                    intent = new Intent(ChooseBus1Activity.this, ChooseBus1Activity.class);
                    intent.putExtra("way", 9);

                } else {
                    intent = new Intent(ChooseBus1Activity.this, TripSummary.class);
                }

                int c = extras.getInt(SelectTripFragment.childCountStr);
                int a = extras.getInt(SelectTripFragment.adultCountStr);
                int i = extras.getInt(SelectTripFragment.infantCountStr);
                Log.d("LOL", String.format("%d, %d, %d", a, c, i));

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

                // initial trip
                if (way == 10) {
                    intent.putExtra("bus_choice_initial_priority", priority);
                    intent.putExtra("bus_choice_initial", (int) selectPos);


                    System.out.println("bus_choice_initial: " + Integer.toString((int) selectPos));
                }
                // return trip
                else {

                    intent.putExtra("bus_choice_initial_priority", extras.getBoolean("bus_choice_initial_priority"));
                    intent.putExtra("bus_choice_initial", extras.getInt("bus_choice_initial"));

                    intent.putExtra("bus_choice_return_priority", priority);
                    intent.putExtra("bus_choice_return", (int) selectPos);


                    System.out.println("bus_choice_return: " + Integer.toString((int) selectPos));
                }

                startActivity(intent);
            }


        }
    }


    private void initializeData() {


//        public static final String fromLoc = "from";
//        public static final String toLoc = "to";
//        public static final String departureDate = "departure_date";
//        public static final String returnDate = "return_date";
//        public static final String adultCount = "adult_count";
//        public static final String childrenCount = "children_count";
//        public static final String infantCount = "infant_count";
//        public static final String isRoundTrip = "is_round_trip";

//        intent.putExtra("from", "New York");
//        intent.putExtra("to", "Hanover");
//        intent.putExtra("departure_date", "06/31/18");
//        intent.putExtra("return_date", "07/25/18");
//        intent.putExtra("adult_count", 2);
//        intent.putExtra("children_count", 2);
//        intent.putExtra("infant_count", 0);
//        intent.putExtra("is_round_trip", false);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String fromLoc = extras.getString("from");
            String toLoc = extras.getString("to");
            Boolean isRoundTrip = extras.getBoolean("is_round_trip");

            int way = extras.getInt("way");
            Log.d("LOL", "" + way);

            // add function to get departure location ie logan for boston, hop for hanover

            // initial trip of a round trip / one way trip
            if (way == 10) {
                String departureDate = extras.getString("departure_date");
                String busStopDepart = extras.getString("bus_stop_depart");
                buses = Bus.getBusArrayList(departureDate, busStopDepart, fromLoc, toLoc);
            }
            // return trip of a round trip
            else if (isRoundTrip && way == 9) {
                String returnDate = extras.getString("return_date");
                String busStopReturn = extras.getString("bus_stop_return");
                buses = Bus.getBusArrayList(returnDate, busStopReturn, toLoc, fromLoc);
            } else {
                throw new AssertionError("Cannot be here");
            }
        }
    }


    private void initializeAdapter() {
        mAdapter = new BusAdapter(this, buses, false);
        listView.setAdapter(mAdapter);

    }

}
