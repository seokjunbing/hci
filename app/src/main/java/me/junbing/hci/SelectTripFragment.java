package me.junbing.hci;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import java.util.Calendar;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectTripFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectTripFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectTripFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // Extras to be put in the intent
    public static final String fromLoc = "from";
    public static final String toLoc = "to";
    public static final String departureDate = "departure_date";
    public static final String returnDate = "return_date";
    public static final String adultCount = "adult_count";
    public static final String childrenCount = "children_count";
    public static final String infantCount = "infant_count";
    public static final String isRoundTrip = "is_round_trip";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String debugTag = "TRIP_SELECT";

    TextView passengerSelectTextView;
    TextView departureDateTextView, returnDateTextView;
    int getPassengersCode = 1;
    SharedPreferences sp;

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
                datePickerOnClick(v);
                break;
            case R.id.return_textview:
                datePickerOnClick(v);
                break;
        }
    }

    // Allows users to select a trip date by tapping on a calendar
    public void datePickerOnClick(final View v) {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(), new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                ((TextView) v).setText(String.format("%d/%d/%d", month, day, year));
            }
        }, year, month, day);
        // earliest trip date -> today
        datePickerDialog.getDatePicker().setMinDate(c.getTime().getTime());
        // latest trip date -> soon
        // TODO
        datePickerDialog.show();
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
                int adultCount = data.getIntExtra(Main2Activity.adultStr, 1);
                int childCount = data.getIntExtra(Main2Activity.childStr, 0);
                int infantCount = data.getIntExtra(Main2Activity.infantStr, 0);
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