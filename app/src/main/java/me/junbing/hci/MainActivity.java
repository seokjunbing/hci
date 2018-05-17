package me.junbing.hci;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener,
        MonthFixturesFragment.OnFragmentInteractionListener,
        SelectTripFragment.OnFragmentInteractionListener,
        WeekFixturesFragment.OnFragmentInteractionListener,
        AllFixturesFragment.OnFragmentInteractionListener {

    TextView departureDatePicker, returnDatePicker;
    TextView passengerCountTextView;
    SharedPreferences sharedPreferences;
    int getPassengersCode = 1;

    int adultCount = 1;
    int childCount = 0;
    int infantCount = 0;

    public static String debugTag = "MAIN_ACT";

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
//                    mTextMessage.setText(R.string.title_home);
//                    selectedFragment = BookingFragment.newInstance();
                    selectedFragment = new FixturesTabs();
                    break;
                case R.id.navigation_my_trips:
//                    mTextMessage.setText(R.string.title_dashboard);
                    selectedFragment = BlankFragment.newInstance();
                    break;
//                    return true;
                case R.id.navigation_tracking:
//                    mTextMessage.setText(R.string.title_notifications);
                    selectedFragment = BlankFragment.newInstance();
                    break;
//                    return true;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.frame_layout, selectedFragment);
            transaction.commit();
            return true;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = getPreferences(MODE_PRIVATE);

//        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        transaction.replace(R.id.frame_layout, BookingFragment.newInstance());
        transaction.replace(R.id.frame_layout, new FixturesTabs());
        transaction.commit();

        departureDatePicker = findViewById(R.id.return_textview);
        returnDatePicker = findViewById(R.id.departure_textview);
        passengerCountTextView = findViewById(R.id.passenger_count_textview);
        Boolean b1, b2, b3;
        b1 = (departureDatePicker == null);
        b2 = (returnDatePicker == null);
        b3 = (passengerCountTextView == null);
        Log.d(debugTag, String.format("%b, %b, %b", b1, b2, b3));

        System.out.println(departureDatePicker);
        System.out.println(returnDatePicker);

//        departureDatePicker.setOnClickListener(this);
//        returnDatePicker.setOnClickListener(this);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        //
    }

    @Override
    public void onClick(View v) {
        if (v == departureDatePicker) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    departureDatePicker.setText("" + year + " " + month + " " + day);
                }
            }, year, month, day);
            datePickerDialog.show();
        }
    }

    static public class SomeDialog extends DialogFragment {
        NumberPicker adultCount;
        NumberPicker childrenCount;
        NumberPicker infantCount;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Log.d("yo", "Hehe");
            return new AlertDialog.Builder(getActivity())
                    .setTitle("Title")
                    .setMessage("Sure you want to do this?")
                    .setView(R.layout.select_passengers_layout)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // do something
                        }
                    }).create();
        }

        public static SomeDialog newInstance() {
            Bundle args = new Bundle();
            SomeDialog fragment = new SomeDialog();
            fragment.setArguments(args);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.select_passengers_layout, container, false);
            adultCount = v.findViewById(R.id.num_adults);
            childrenCount = v.findViewById(R.id.num_children);
            infantCount = v.findViewById(R.id.num_infants);

            Log.d("yo", adultCount != null ? "Num picker" : "Still null");
            getActivity().getPreferences(MODE_PRIVATE).edit().putInt("adults", 0).commit();
//            configureNumberPicker(adultCountStr, 0, 6);
//            configureNumberPicker(childCountStr, 0, 3);
//            configureNumberPicker(infantCountStr, 0, 3);
//
//            if (adultCountStr != null) {
//                adultCountStr.setMinValue(1);
//                adultCountStr.setMaxValue(6);
//                Log.d("yo", "Max: " + adultCountStr.getMaxValue() + ". Min: " + adultCountStr.getMinValue());
//                adultCountStr.setOnValueChangedListener(onValueChangeListener);
//                adultCountStr.setWrapSelectorWheel(true);
//                Log.d("yo", "worked?");
//            } else {
//                Log.d("yo", "did not work lol");
//            }
            return v;
        }

        private void configureNumberPicker(final NumberPicker np, int minValue, int maxValue) {
            final String[] values = {"Red", "Green", "Blue", "Yellow", "Magenta"};
            np.setMaxValue(maxValue);
            np.setMinValue(minValue);
//            np.setMaxValue(values.length - 1);
//            np.setMinValue(0);
//            np.setDisplayedValues(values);
            np.setWrapSelectorWheel(true);
//            StringBuilder valString = new StringBuilder();
//            for (Object o : values) {
//                valString.append(", ").append(o.toString());
//            }
//            Log.d("yo", "values: " + valString.toString());
//            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//                @Override
//                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                    //Display the newly selected value from picker
//                    Log.d("yo", "Selected value : " + values[newVal]);
//                }
//            });
            np.setOnValueChangedListener(onValueChangeListener);
        }

        NumberPicker.OnValueChangeListener onValueChangeListener =
                new NumberPicker.OnValueChangeListener() {
                    @Override
                    public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                        Toast.makeText(
                                getContext(), "Numbers: " + i + ", " + i1, Toast.LENGTH_LONG
                        ).show();

                    }
                };

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            Log.d(MainActivity.debugTag, "onViewCreated");
            NumberPicker ad = view.findViewById(R.id.num_adults);
            super.onViewCreated(view, savedInstanceState);
            Log.d(MainActivity.debugTag, ad.toString());
            configureNumberPicker(ad, 0, 6);
            ad.setValue(4);
//            TextView t = view.findViewById(R.id.)
        }
    }
}
