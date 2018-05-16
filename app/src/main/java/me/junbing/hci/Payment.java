package me.junbing.hci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Payment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        findViewById(R.id.purchase).setOnClickListener(new HandlePurchaseClick());
    }




    private class  HandlePurchaseClick implements View.OnClickListener {
        public void onClick(View arg0) {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                Intent intent = new Intent(Payment.this, Confirmation.class);


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

                intent.putExtra("bus_choice_initial_priority", extras.getBoolean("bus_choice_initial_priority"));
                intent.putExtra("bus_choice_initial", extras.getInt("bus_choice_initial"));

                intent.putExtra("bus_choice_return_priority", extras.getBoolean("bus_choice_return_priority"));
                intent.putExtra("bus_choice_return", extras.getInt("bus_choice_return"));

                startActivity(intent);
            }
        }
    }
}
