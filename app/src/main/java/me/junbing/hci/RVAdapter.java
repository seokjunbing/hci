package me.junbing.hci;

import android.graphics.Color;
import android.provider.MediaStore;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

// referred to
// https://code.tutsplus.com/tutorials/getting-started-with-recyclerview-and-cardview-on-android--cms-23465


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.BusViewHolder> {
    private int selectedPos = RecyclerView.NO_POSITION;

    class BusViewHolder extends RecyclerView.ViewHolder {

        CardView cv;

        TextView time;
        TextView departure_loc;
        TextView direction;

        final RadioGroup rg;
        final RadioButton rb1;
        final RadioButton rb2;

        BusViewHolder(final View itemView) {
            super(itemView);

            cv = (CardView)itemView.findViewById(R.id.cv);

            time = (TextView)itemView.findViewById(R.id.time);
            departure_loc = (TextView)itemView.findViewById(R.id.departure_loc);
            direction = (TextView)itemView.findViewById(R.id.direction);

            rg = itemView.findViewById(R.id.radioGroup);
            rb1 = itemView.findViewById(R.id.radioButton1);
            rb2 = itemView.findViewById(R.id.radioButton2);


            // Handle item click and set the selection
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {
                    if (getAdapterPosition() == RecyclerView.NO_POSITION) return;
//                    getAdapterPosition();

//                    notifyItemChanged(selectedPos);
                    selectedPos = getAdapterPosition();
//                    notifyItemChanged(selectedPos);

                    notifyDataSetChanged();
//                    notifyItemChanged(selectedPos);
//                    notifyDataSetChanged();

                    Toast.makeText(itemView.getContext(),
                            "Pos: " + Integer.toString(selectedPos),
                            Toast.LENGTH_SHORT).show();

                }
            });
//
            rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(final RadioGroup group, final int checkedId) {
                    switch (checkedId) {
                        case R.id.radioButton1:
                            buses.get(selectedPos).priority=false;
                            ((RadioButton)group.getChildAt(0)).setChecked(true);
                            ((RadioButton)group.getChildAt(1)).setChecked(false);
                            break;
                        case R.id.radioButton2:
                            buses.get(selectedPos).priority=true;
                            ((RadioButton)group.getChildAt(0)).setChecked(false);
                            ((RadioButton)group.getChildAt(1)).setChecked(true);
                            break;
                    }

                    for(int i=0; i<buses.size(); i++) {
                        Log.d("stat", Boolean.toString(buses.get(i).priority));
                    }

                }
            });



        }


    }

    List<Bus> buses;

    RVAdapter(List<Bus> buses){
        this.buses = buses;
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public BusViewHolder onCreateViewHolder(final ViewGroup viewGroup, final int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        BusViewHolder pvh = new BusViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(final BusViewHolder busViewHolder, final int i) {
        busViewHolder.time.setText(buses.get(i).time);
        busViewHolder.departure_loc.setText(buses.get(i).departure_loc);
        busViewHolder.direction.setText(buses.get(i).direction);
        RadioGroup rg = busViewHolder.itemView.findViewById(R.id.radioGroup);


//        busViewHolder.itemView.setSelected(selectedPos == i);


        if(selectedPos == i) {
            busViewHolder.cv.setCardBackgroundColor(Color.parseColor("#567845"));


            busViewHolder.itemView.findViewById(R.id.radioButton1).setClickable(true);
            busViewHolder.itemView.findViewById(R.id.radioButton2).setClickable(true);

            rg.getChildAt(0).setEnabled(true);
            rg.getChildAt(1).setEnabled(true);
        }
        else {
            busViewHolder.cv.setCardBackgroundColor(Color.parseColor("#ffffff"));

            busViewHolder.itemView.findViewById(R.id.radioButton1).setClickable(false);
            busViewHolder.itemView.findViewById(R.id.radioButton2).setClickable(false);

            rg.getChildAt(0).setEnabled(false);
            rg.getChildAt(1).setEnabled(false);
        }

        if(buses.get(i).priority) {
            ((RadioButton)busViewHolder.itemView.findViewById(R.id.radioButton1)).setChecked(false);
            ((RadioButton)busViewHolder.itemView.findViewById(R.id.radioButton2)).setChecked(true);
        }
        else {
            ((RadioButton)busViewHolder.itemView.findViewById(R.id.radioButton1)).setChecked(true);
            ((RadioButton)busViewHolder.itemView.findViewById(R.id.radioButton2)).setChecked(false);
        }



    }




    @Override
    public int getItemCount() {
        return buses.size();
    }
}
