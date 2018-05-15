package me.junbing.hci;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BookingFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BookingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BookingFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BookingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment BookingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BookingFragment newInstance() {
        BookingFragment fragment = new BookingFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_booking_1, container, false);
        v.findViewById(R.id.search_button).setOnClickListener(new HandleSearchClick());

        v.findViewById(R.id.pay_button).setOnClickListener(new HandlePayClick());
        v.findViewById(R.id.trip_summary).setOnClickListener(new HandleSummaryClick());

        return v;
    }

    private class HandleSearchClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Toast.makeText(getActivity(), "pressed search", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), ChooseBus1Activity.class);
            startActivity(intent);

        }
    }

    private class HandlePayClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Toast.makeText(getActivity(), "pressed pay", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Payment.class);
            startActivity(intent);

        }
    }

    private class HandleSummaryClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Toast.makeText(getActivity(), "pressed pay", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), TripSummary.class);
            startActivity(intent);

        }
    }

//    private class HandleClick implements View.OnClickListener {
//        public void onClick(View arg0) {
//            Button btn = (Button)arg0;  //cast view to a button
//            // get a reference to the TextView
//            TextView tv = (TextView) findViewById(R.id.textview1);
//            // update the TextView text
//            tv.setText("You pressed " + btn.getText());
//        }
//    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }

//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }

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
}
