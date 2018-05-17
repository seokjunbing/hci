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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    public static final String dateFormat = "MM/dd/yyyy";

    private static final String ARG_TRIP_TYPE = "trip_type";
    public static String debugTag = "TRIP_SELECT";

    private int adultCount = 1;
    private int childCount = 0;
    private int infantCount = 0;
    String[] passengerLabelArr = {"adult", "child", "infant"};

    // Interface elements
    private TextView passengerSelectTextView;
    private TextView departureDateTextView, returnDateTextView;
    private LinearLayout departureSelectLayout, returnSelectLayout;
    private Button searchButton;
    Spinner fromSpinner, toSpinner;

    public int getPassengersCode = 1;
    SharedPreferences sp;

    private String fromStr, toStr, fromBusStop, toBusStop;
    // used to force users to select trips returning after their start date
    private Calendar departureDateCalendar, returnDateCalendar;
    // TODO: Rename and change types of parameters
    private String mParam1;

    public static final String ONE_WAY = "one-way";
    public static final String ROUND_TRIP = "round-trip";

    private OnFragmentInteractionListener mListener;

    public SelectTripFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1
     * @return A new instance of fragment SelectTripFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelectTripFragment newInstance(String param1) {
        SelectTripFragment fragment = new SelectTripFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TRIP_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_TRIP_TYPE);
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


        departureSelectLayout = view.findViewById(R.id.departure_select_mini_layout);
        returnSelectLayout = view.findViewById(R.id.arrival_select_mini_layout);

        fromSpinner = view.findViewById(R.id.from_spinner);
        toSpinner = view.findViewById(R.id.to_spinner);
        toSpinner.setSelection(3); // 3 -> Boston Logan

        if (mParam1 != null && mParam1.equals(ONE_WAY)) {
            // get the departure date form to fill the horizontal space
            departureSelectLayout.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            departureDateTextView.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
            returnSelectLayout.setVisibility(View.GONE);
        }

        toSpinner.setOnItemSelectedListener(this);
        fromSpinner.setOnItemSelectedListener(this);

        searchButton = view.findViewById(R.id.trip_search_button);
        searchButton.setOnClickListener(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_select_trip, container, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.passenger_count_textview:
                passengerSelectOnClick(v);
                break;
            case R.id.departure_textview:
                pickDateDialog((TextView) v, Calendar.getInstance(), returnDateCalendar); // min = today
                break;
            case R.id.return_textview:
                Calendar minDate = (departureDateCalendar != null) ?
                        departureDateCalendar : Calendar.getInstance();
                pickDateDialog((TextView) v, minDate, null);
                break;
            case R.id.trip_search_button:
                searchForTrips();
                break;
        }
    }

    private void searchForTrips() {
        Intent intent = new Intent(getContext(), ChooseBus1Activity.class);

        String departureDateStr = new SimpleDateFormat(
                dateFormat, Locale.getDefault()).format(departureDateCalendar.getTime());
        intent.putExtra(SelectTripFragment.departureDateStr, departureDateStr);
        // this field is included in the intent only if the trip is a round-trip one
        if (mParam1.equals(ROUND_TRIP)) {
            String returnDateStr = new SimpleDateFormat(
                    dateFormat, Locale.getDefault()).format(returnDateCalendar.getTime());
            intent.putExtra(SelectTripFragment.returnDateStr, returnDateStr);
        }

        intent.putExtra(fromLocStr, fromStr);
        intent.putExtra(toLocStr, toStr);
        intent.putExtra(busStopDepartureStr, fromBusStop);
        intent.putExtra(busStopReturnStr, toBusStop);
        intent.putExtra(wayStr, 10);

        intent.putExtra(adultCountStr, adultCount);
        intent.putExtra(childCountStr, childCount);
        intent.putExtra(infantCountStr, infantCount);
        // TODO changed for testing
        intent.putExtra(isRoundTripStr, true);
        startActivity(intent);
    }

    /**
     * Allows users to select a trip date by tapping on a calendar
     * <p>
     * If either minDate or maxDate are null, no minimum or maximum
     * dates will be set for the calendar.
     *
     * @param view    view to update with the resulting date
     * @param minDate minimum date users are allowed to select.
     * @param maxDate maximum date users are allowed to select.
     */
    public void pickDateDialog(final TextView view, @Nullable Calendar minDate, @Nullable Calendar maxDate) {
        final Calendar calendarToday = Calendar.getInstance();
        int year = calendarToday.get(Calendar.YEAR);
        int month = calendarToday.get(Calendar.MONTH);
        int day = calendarToday.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Update the text shown to the user and save the dates
                Calendar chosenDateCalendar = Calendar.getInstance();
                chosenDateCalendar.set(year, month, day);
                ((TextView) view).setText(new SimpleDateFormat(
                        dateFormat, Locale.getDefault()).format(chosenDateCalendar.getTime()
                ));

                if (view.getId() == R.id.departure_textview) {
                    departureDateCalendar = chosenDateCalendar;
                    if (mParam1.equals(ONE_WAY) || returnDateCalendar != null) {
                        searchButton.setEnabled(true);
                    }
                } else if (view.getId() == R.id.return_textview) {
                    returnDateCalendar = chosenDateCalendar;
                    if (departureDateCalendar != null) {
                        searchButton.setEnabled(true);
                    }
                }
            }
        }, year, month, day);

        // Handle limiting the range of the departure or return trips after one
        // of the fields has been set
        if (minDate != null) {
            datePickerDialog.getDatePicker().setMinDate(minDate.getTimeInMillis());
        }
        if (maxDate != null) {
            datePickerDialog.getDatePicker().setMaxDate(maxDate.getTimeInMillis());
        }

        // Set the currently selected date in the calendar (if user selected it previously)
        if (view.getId() == R.id.departure_textview && departureDateCalendar != null) {
            datePickerDialog.updateDate(
                    departureDateCalendar.get(Calendar.YEAR),
                    departureDateCalendar.get(Calendar.MONTH),
                    departureDateCalendar.get(Calendar.DAY_OF_MONTH)
            );
        }

        if (view.getId() == R.id.return_textview && returnDateCalendar != null) {
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

                int possibleLocationsResourceId = getAvailableDestinations(fromLocation);
                String[] posLoc = getResources().getStringArray(possibleLocationsResourceId);
                ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(
                        getContext(),
                        android.R.layout.simple_spinner_item,
                        posLoc
                );
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                toSpinner.setAdapter(spinnerAdapter);

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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.d(debugTag, "Nothing selected");
    }

    @Nullable
    private Integer getAvailableDestinations(String from) {
        String fromLower = from.toLowerCase();
        if (fromLower.equals("logan airport") || fromLower.equals("south station")) {
            return R.array.bus_stops_from_boston;
        } else if (fromLower.equals("hanover") || fromLower.equals("lebanon")) {
            return R.array.bus_stops_from_upper_valley;
        } else if (fromLower.equals("new london")) {
            return R.array.bus_stops_from_new_london;
        } else if (fromLower.equals("new york city")) {
            return R.array.bus_stops_from_ny;
        } else {
            return null;
        }
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
                return "Logan Airport";
            case "south station":
                return "South Station";
            case "new york city":
                return "150 East 42nd Street";
            default:
                return null;
        }
    }

    public void passengerSelectOnClick(View v) {
        Intent intent = new Intent(getContext(), PickPassengersActivity.class);
        intent.putExtra(adultCountStr, adultCount);
        intent.putExtra(childCountStr, childCount);
        intent.putExtra(infantCountStr, infantCount);
        startActivityForResult(intent, getPassengersCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (getPassengersCode == requestCode) {
            if (resultCode == RESULT_OK) {

                adultCount = data.getIntExtra(PickPassengersActivity.adultStr, 1);
                childCount = data.getIntExtra(PickPassengersActivity.childStr, 0);
                infantCount = data.getIntExtra(PickPassengersActivity.infantStr, 0);
                // array so that we can loop over all the passenger strings and have
                // a dynamic number of passenger strings represented easily
                int[] passengerCountArr = {adultCount, childCount, infantCount};

                // format all the passenger strings
                ArrayList<String> passengerStringArr = new ArrayList<>(3);
                for (int i = 0; i < passengerCountArr.length; i++) {
                    if (passengerCountArr[i] > 0) {
                        passengerStringArr.add(
                                formatPassengerCount(passengerLabelArr[i], passengerCountArr[i])
                        );
                    }
                }
                passengerSelectTextView.setText(String.join(", ", passengerStringArr));
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