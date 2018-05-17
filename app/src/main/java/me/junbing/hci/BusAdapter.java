package me.junbing.hci;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
        final ImageButton question_button;

        time = (TextView)listItem.findViewById(R.id.time);
        departure_loc = (TextView)listItem.findViewById(R.id.departure_loc);
        direction = (TextView)listItem.findViewById(R.id.direction);
        date = (TextView)listItem.findViewById(R.id.date);
        summaryPriority = listItem.findViewById(R.id.summary_priority);
        rg = listItem.findViewById(R.id.radioGroup);

        question_button = listItem.findViewById(R.id.help_button);
//        question_button.getDrawable().mutate().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

        if(isSummary) {
            rg.setVisibility(View.GONE);

            if(BusList.get(position).priority) {
                summaryPriority.setText("Priority Boarding");
            }
            else {
                summaryPriority.setText("Standard Boarding");
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

        // referred to
        // https://stackoverflow.com/questions/2115758/how-do-i-display-an-alert-dialog-on-android?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
        question_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(mContext, android.R.style.ThemeOverlay_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(mContext);
                }
                builder.setTitle("Priority Boarding")
                        .setMessage("Jump the line and board the bus earlier for an extra $10.")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // empty
                            }
                        })
                        .show();
            }
        });





        return listItem;
    }

}
