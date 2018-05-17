package me.junbing.hci;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements
        SelectTripFragment.OnFragmentInteractionListener,
        AllFixturesFragment.OnFragmentInteractionListener {

    TextView tv;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    tv.setText("Book a Bus Trip");
                    selectedFragment = new TripTypeTabs();
                    break;
                case R.id.navigation_my_trips:
                    tv.setText("Your Trips");
                    selectedFragment = BlankFragment.newInstance();
                    break;
                case R.id.navigation_tracking:
                    tv.setText("Track your Bus");
                    selectedFragment = BlankFragment.newInstance();
                    break;
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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        tv = findViewById(R.id.feature_title);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, new TripTypeTabs());
        transaction.commit();
    }

    /**
     * Fragments require this be implemented
     */
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
