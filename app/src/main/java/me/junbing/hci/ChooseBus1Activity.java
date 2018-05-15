package me.junbing.hci;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

// referred https://medium.com/mindorks/custom-array-adapters-made-easy-b6c4930560dd

public class ChooseBus1Activity extends AppCompatActivity {

//    private List<Bus> buses;
//    private RecyclerView rv;
    private ListView listView;
    private BusAdapter mAdapter;
    ArrayList<Bus> buses = new ArrayList<>();

    private long selectPos = ListView.NO_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        Recycle View
//        setContentView(R.layout.activity_choose_bus1);
//        rv=(RecyclerView)findViewById(R.id.rv);
//        LinearLayoutManager llm = new LinearLayoutManager(this);
//        rv.setLayoutManager(llm);
//        rv.setHasFixedSize(true);
//        initializeData();
//        initializeAdapter();

        setContentView(R.layout.activity_choose_bus1);
        listView = (ListView) findViewById(R.id.choose_bus_lv);
        initializeData();
        initializeAdapter();




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, final long id) {
//                selectPos = id;

                view.findViewById(R.id.radioButton1).setClickable(true);
                view.findViewById(R.id.radioButton2).setClickable(true);

                Toast.makeText(ChooseBus1Activity.this, "pos: " + Long.toString(id), Toast.LENGTH_SHORT).show();

                System.out.println("child count: " + Integer.toString(listView.getChildCount()));
                for (int i = 0; i < listView.getChildCount(); i++) {
                    if((int)id != i) {
                        View v = listView.getChildAt(i);
                        if(v != null) {
                            v.findViewById(R.id.radioButton1).setClickable(false);
                            v.findViewById(R.id.radioButton2).setClickable(false);
                        }
                    }
                }

            }
        });
    }

    private void initializeData(){
        buses = Bus.getBusArrayList("11/30/18", "Logan Airport", "BOS -> HAN");
    }

    private void initializeAdapter(){
//        RVAdapter adapter = new RVAdapter(buses);
//        rv.setAdapter(adapter);

        mAdapter = new BusAdapter(this, buses, false);
        listView.setAdapter(mAdapter);

    }

}
