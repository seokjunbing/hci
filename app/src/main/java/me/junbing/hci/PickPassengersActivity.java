package me.junbing.hci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

public class PickPassengersActivity extends AppCompatActivity {
    //    NumberPickerXML adultPicker, childrenPicker, infantPicker;
    Spinner adultPicker, childPicker, infantPicker;
    int numAdults, numChildren, numInfants;

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
        String vars = "adults, kids, infants: "
                + numAdults + ", " + numChildren + ", " + numInfants;
        Toast.makeText(getApplicationContext(), vars, Toast.LENGTH_LONG).show();

        Intent data = new Intent();
        data.putExtra(adultStr, numAdults);
        data.putExtra(childStr, numChildren);
        data.putExtra(infantStr, numInfants);
        setResult(RESULT_OK, data);
        finish();
    }
}
