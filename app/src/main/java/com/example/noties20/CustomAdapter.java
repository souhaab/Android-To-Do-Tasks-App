package com.example.noties20;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String date;
    private Activity activity;
    private ArrayList note_id, note_title, note_text, note_time;

    CustomAdapter(Activity activity, Context context, ArrayList note_id, ArrayList note_title, ArrayList note_text,ArrayList note_time){
        this.activity = activity;
        this.context = context;
        this.note_id = note_id;
        this.note_title = note_title;
        this.note_text = note_text;
        this.note_time = note_time;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, int position) {
        holder.note_title_txt.setText(String.valueOf(note_title.get(position)));
        holder.note_text_txt.setText(String.valueOf(note_text.get(position)));
        holder.note_time_txt.setText(String.valueOf(note_time.get(position)));
        //Recyclerview onClickListener
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(note_id.get(position)));
                intent.putExtra("title", String.valueOf(note_title.get(position)));
                intent.putExtra("text", String.valueOf(note_text.get(position)));
                intent.putExtra("time", String.valueOf(note_time.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });


    }

    @Override
    public int getItemCount() {
        return note_id.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView note_title_txt, note_text_txt, note_time_txt;
        RelativeLayout mainLayout;

        MyViewHolder(@NonNull View itemView) {
            super(itemView);
            note_title_txt = itemView.findViewById(R.id.note_title_txt);
            note_text_txt = itemView.findViewById(R.id.note_text_txt);
            note_time_txt = itemView.findViewById(R.id.note_time_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animate Recyclerview
            Animation translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }

    }


}
