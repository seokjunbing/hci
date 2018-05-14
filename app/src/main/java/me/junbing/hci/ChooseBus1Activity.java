package me.junbing.hci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ChooseBus1Activity extends AppCompatActivity {

    private List<Bus> buses;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_choose_bus1);

        rv=(RecyclerView)findViewById(R.id.rv);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData(){
        buses = new ArrayList<>();
        buses.add(new Bus("8AM - 11AM", "Logan Airport", "BOS -> HAN", Boolean.FALSE, Boolean.FALSE));
        buses.add(new Bus("10AM - 1PM", "Logan Airport", "BOS -> HAN", Boolean.FALSE, Boolean.FALSE));
        buses.add(new Bus("12PM - 3PM", "Logan Airport", "BOS -> HAN", Boolean.FALSE, Boolean.FALSE));
        buses.add(new Bus("2PM - 5PM", "Logan Airport", "BOS -> HAN", Boolean.FALSE, Boolean.FALSE));
        buses.add(new Bus("4PM - 7PM", "Logan Airport", "BOS -> HAN", Boolean.FALSE, Boolean.FALSE));
        buses.add(new Bus("6PM - 9PM", "Logan Airport", "BOS -> HAN", Boolean.FALSE, Boolean.FALSE));
    }

    private void initializeAdapter(){
        RVAdapter adapter = new RVAdapter(buses);
        rv.setAdapter(adapter);
    }

    public void MyOnClickListener() {
        Toast.makeText(this, "yololol", Toast.LENGTH_SHORT).show();
    }


}
