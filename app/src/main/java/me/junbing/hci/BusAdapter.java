package me.junbing.hci;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class BusAdapter extends ArrayAdapter<Bus> {
    private Context mContext;
    private List<Bus> BusList;
    private Boolean isSummary;

    public BusAdapter(@NonNull Context context, @LayoutRes ArrayList<Bus> list, Boolean isSummary) {
        super(context, 0 , list);
        mContext = context;
        BusList = list;
        this.isSummary = isSummary;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.item,parent,false);


        TextView time;
        TextView departure_loc;
        TextView direction;
        TextView date;
        TextView summaryPriority;

        final RadioGroup rg;

        time = (TextView)listItem.findViewById(R.id.time);
        departure_loc = (TextView)listItem.findViewById(R.id.departure_loc);
        direction = (TextView)listItem.findViewById(R.id.direction);
        date = (TextView)listItem.findViewById(R.id.date);
        summaryPriority = listItem.findViewById(R.id.summary_priority);
        rg = listItem.findViewById(R.id.radioGroup);

        if(isSummary) {
            rg.setVisibility(View.GONE);

            if(BusList.get(position).priority) {
                summaryPriority.setText("Priority");
            }
            else {
                summaryPriority.setText("Standard");
            }
        }
        else {
            summaryPriority.setVisibility(View.GONE);

        }



        final Bus currentBus = BusList.get(position);

        time.setText(currentBus.time);
        departure_loc.setText(currentBus.departure_loc);
        direction.setText(currentBus.direction);
        date.setText(currentBus.date);

        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                switch (checkedId) {
                    case R.id.radioButton1:
                        currentBus.priority=false;
//                        ((RadioButton)group.getChildAt(0)).setChecked(true);
//                        ((RadioButton)group.getChildAt(1)).setChecked(false);
                        break;
                    case R.id.radioButton2:
                        currentBus.priority=true;
//                        ((RadioButton)group.getChildAt(0)).setChecked(false);
//                        ((RadioButton)group.getChildAt(1)).setChecked(true);
                        break;
                }

                for(int i=0; i<BusList.size(); i++) {
                    Log.d("Debug", Boolean.toString(BusList.get(i).priority));
                }

            }
        });



        return listItem;
    }

}
