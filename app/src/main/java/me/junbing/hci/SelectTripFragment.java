package me.junbing.hci;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTripFragment extends Fragment implements View.OnClickListener, OnItemSelectedListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // Extras to be put in the intent
    public static final String fromLocStr = "from";
    public static final String toLocStr = "to";
    public static final String busStopDepartureStr = "bus_stop_depart";
    public static final String busStopReturnStr = "bus_stop_return";
    public static final String departureDateStr = "departure_date";
    public static final String returnDateStr = "return_date";
    public static final String adultCountStr = "adult_count";
    public static final String childCountStr = "children_count";
    public static final String infantCountStr = "infant_count";
    public static final String isRoundTripStr = "is_round_trip";
    public static final String wayStr = "way";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String debugTag = "TRIP_SELECT";


    private int adultCount;
    private int childCount;
    private int infantCount;


    // Interface elements
    private TextView passengerSelectTextView;
    private TextView departureDateTextView, returnDateTextView;
    private Button searchButton;
//    private Spinner fromSpinner, toSpinner;

    public int getPassengersCode = 1;
    SharedPreferences sp;

    private String fromStr, toStr, fromBusStop, toBusStop;
    // used to force users to select trips returning after their start date
    private Calendar departureDateCalendar, returnDateCalendar;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public SelectTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelectTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTripFragment newInstance(String param1, String param2) {
        SelectTripFragment fragment = new SelectTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        sp = getActivity().getPreferences(Context.MODE_PRIVATE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        passengerSelectTextView = view.findViewById(R.id.passenger_count_textview);
        passengerSelectTextView.setOnClickListener(this);

        departureDateTextView = view.findViewById(R.id.departure_textview);
        departureDateTextView.setOnClickListener(this);
        returnDateTextView = view.findViewById(R.id.return_textview);
        returnDateTextView.setOnClickListener(this);

        Spinner fromSpinner = view.findViewById(R.id.from_spinner);
        Spinner toSpinner = view.findViewById(R.id.to_spinner);
        toSpinner.setOnItemSelectedListener(this);
        fromSpinner.setOnItemSelectedListener(this);

        searchButton = view.findViewById(R.id.trip_search_button);
        searchButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todays_fixtures, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passenger_count_textview:
                passengerSelectOnClick(v);
                break;
            case R.id.departure_textview:
                pickDateDialog(v, Calendar.getInstance());
                break;
            case R.id.return_textview:
                pickDateDialog(v, departureDateCalendar);
                break;
            case R.id.trip_search_button:
                searchForTrips();
                break;
        }
    }

    private void searchForTrips() {
        String departureDateStr = new SimpleDateFormat(
                "MM/dd/yyyy", Locale.getDefault()).format(departureDateCalendar.getTime());
        String returnDateStr = new SimpleDateFormat(
                "MM/dd/yyyy", Locale.getDefault()).format(returnDateCalendar.getTime());


        // TODO hook the class here
        Intent intent = new Intent(getContext(), ChooseBus1Activity.class);
        intent.putExtra(fromLocStr, fromStr);
        intent.putExtra(toLocStr, toStr);
        intent.putExtra(busStopDepartureStr, fromBusStop);
        intent.putExtra(wayStr, 10);

        intent.putExtra(busStopReturnStr, busStopReturnStr);
        intent.putExtra(SelectTripFragment.departureDateStr, departureDateStr);
        intent.putExtra(SelectTripFragment.returnDateStr, returnDateStr);
        intent.putExtra(adultCountStr, adultCount);
        intent.putExtra(childCountStr, childCount);
        intent.putExtra(infantCountStr, infantCount);
        intent.putExtra(isRoundTripStr, true);
        startActivity(intent);
    }

    // Allows users to select a trip date by tapping on a calendar
    public void pickDateDialog(final View v, Calendar minDate) {
        final Calendar calendarToday = Calendar.getInstance();
        int year = calendarToday.get(Calendar.YEAR);
        int month = calendarToday.get(Calendar.MONTH);
        int day = calendarToday.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                ((TextView) v).setText(String.format("%d/%d/%d", month, day, year));
                // USE THIS CALENDAR TO SET THE MIN DATE FOR THE OTHER ONE
                if (v.getId() == R.id.departure_textview) {
                    departureDateCalendar = Calendar.getInstance();
                    departureDateCalendar.set(year, month, day);
                } else if (v.getId() == R.id.return_textview) {
                    returnDateCalendar = Calendar.getInstance();
                    returnDateCalendar.set(year, month, day);
                }
            }
        }, year, month, day);
        // earliest trip date -> today, regardless of whether this is the departure or return calendar
        datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        // earliest trip for return trip is after the departure trip
        if (v.getId() == R.id.return_textview && departureDateCalendar != null) {
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        }

        // Set the currently selected date in the calendar (if user selected it previously)
        if (v.getId() == R.id.departure_textview && departureDateCalendar != null) {
            datePickerDialog.updateDate(
                    departureDateCalendar.get(Calendar.YEAR),
                    departureDateCalendar.get(Calendar.MONTH),
                    departureDateCalendar.get(Calendar.DAY_OF_MONTH)
            );
        }

        if (v.getId() == R.id.return_textview && returnDateCalendar != null) {
            datePickerDialog.updateDate(
                    returnDateCalendar.get(Calendar.YEAR),
                    returnDateCalendar.get(Calendar.MONTH),
                    returnDateCalendar.get(Calendar.DAY_OF_MONTH)
            );
        }

        datePickerDialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.from_spinner:
                String fromLocation = (String) parent.getItemAtPosition(position);
                fromStr = busStopToCity(fromLocation);
                fromBusStop = busStopToStation(fromLocation);
                Log.d(debugTag, fromStr + ", " + fromBusStop);
                break;
            case R.id.to_spinner:
                String toLocation = (String) parent.getItemAtPosition(position);
                toStr = busStopToCity(toLocation);
                toBusStop = busStopToStation(toLocation);
                Log.d(debugTag, toStr + ", " + toBusStop);
                break;
            default:
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(debugTag, "Nothing selected");
    }

    private String busStopToCity(String busStop) {
        switch (busStop.toLowerCase()) {
            case "hanover":
                return "HANOVER";
            case "lebanon":
                return "LEBANON";
            case "new london":
                return "NEW LONDON";
            case "logan airport":
                return "BOSTON";
            case "south station":
                return "BOSTON";
            case "new york city":
                return "NEW YORK";
            default:
                return null;
        }
    }

    private String busStopToStation(String busStop) {
        switch (busStop.toLowerCase()) {
            case "hanover":
                return "Hopkins Center";
            case "lebanon":
                return "Lebanon Transportation Center";
            case "new london":
                return "NH Park & Ride (Exit 12, I-89)";
            case "logan airport":
                return "Terminals A, B1, B2, C, E";
            case "south station":
                return "700 Atlantic Ave";
            case "new york city":
                return "150 East 42nd Street";
            default:
                return null;
        }
    }

    // START OF COPYPASTE
    public void passengerSelectOnClick(View v) {
        Intent intent = new Intent(getContext(), Main2Activity.class);
        startActivityForResult(intent, getPassengersCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getPassengersCode == requestCode) {
            if (resultCode == RESULT_OK) {
                adultCount = data.getIntExtra(Main2Activity.adultStr, 1);
                childCount = data.getIntExtra(Main2Activity.childStr, 0);
                infantCount = data.getIntExtra(Main2Activity.infantStr, 0);
                passengerSelectTextView.setText(
                        String.format("%s, %s, %s",
                                formatPassengerCount("adult", adultCount),
                                formatPassengerCount("child", childCount),
                                formatPassengerCount("infant", infantCount)
                        )
                );
            }
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /* UTILITIES */

    /**
     * @param passengerType Assumed to be "adult", "child", or "infant"
     * @param count         number of passengers of this type
     * @return A formatted string of the form "1 adult", "2 adults", or "" (if count = 0)
     */
    private String formatPassengerCount(String passengerType, int count) {
        if (count == 0) {
            return "";
        } else if (count == 1) {
            return "1 " + passengerType;
        } else {
            String pluralPassengerStr = (passengerType.equals("child")) ? "children" : passengerType + "s";
            return String.format("%d %s", count, pluralPassengerStr);
        }
    }
}