package me.junbing.hci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class PickPassengersActivity extends AppCompatActivity {
    Spinner adultPicker, childPicker, infantPicker;
    int numAdults, numChildren, numInfants;

    TextView alertNoPassengersTextView;
    public static String adultStr = "adults";
    public static String childStr = "children";
    public static String infantStr = "infants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_passengers);

        adultPicker = findViewById(R.id.adult_numpicker);
        childPicker = findViewById(R.id.children_numpicker);
        infantPicker = findViewById(R.id.infant_numpicker);
        alertNoPassengersTextView = findViewById(R.id.alert_no_passengers);

        // Set default values for number of passengers based on what the passengers
        // had previously chosen
        Bundle extras = getIntent().getExtras();
        numAdults = extras.getInt(SelectTripFragment.adultCountStr, 1);
        numChildren = extras.getInt(SelectTripFragment.childCountStr, 0);
        numInfants = extras.getInt(SelectTripFragment.infantCountStr, 0);
        adultPicker.setSelection(numAdults);
        childPicker.setSelection(numChildren);
        infantPicker.setSelection(numInfants);

        adultPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numAdults = Integer.parseInt((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        childPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numChildren = Integer.parseInt((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        infantPicker.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numInfants = Integer.parseInt((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }


    public void OkOnClick(View v) {
        if (numAdults == 0 && numChildren == 0 && numInfants == 0) {
            alertNoPassengersTextView.setVisibility(View.VISIBLE);
        } else {
            Intent returnData = new Intent();
            returnData.putExtra(adultStr, numAdults);
            returnData.putExtra(childStr, numChildren);
            returnData.putExtra(infantStr, numInfants);
            setResult(RESULT_OK, returnData);
            finish();
        }
    }
}
