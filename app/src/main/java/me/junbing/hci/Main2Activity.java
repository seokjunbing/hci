package me.junbing.hci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    NumberPickerXML adultPicker, childrenPicker, infantPicker;
    int numAdults, numChildren, numInfants;

    public static String adultStr = "adults";
    public static String childStr = "children";
    public static String infantStr = "infants";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        adultPicker = findViewById(R.id.adult_numpicker);
        childrenPicker = findViewById(R.id.children_numpicker);
        infantPicker = findViewById(R.id.infant_numpicker);

        adultPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                numAdults = newVal;
            }
        });

        childrenPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                numChildren = newVal;
            }
        });

        infantPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int oldVal, int newVal) {
                numInfants = newVal;
            }
        });
    }

    public void OkOnClick(View v) {
        String vars = "adults, kids, infants: "
                + numAdults + ", " + numChildren + ", " + numInfants;
        Toast.makeText(getApplicationContext(), vars.toString(), Toast.LENGTH_LONG).show();

        Intent data = new Intent();
        data.putExtra(adultStr, numAdults);
        data.putExtra(childStr, numChildren);
        data.putExtra(infantStr, numInfants);
        setResult(RESULT_OK, data);
        finish();
    }
}
