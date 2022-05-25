package com.example.noties20;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList task_id, task_text, task_status;
    private MyDatabaseHelper myDB;

    TaskAdapter(Activity activity, Context context, ArrayList task_id, ArrayList task_text, ArrayList task_status){
        this.activity = activity;
        this.context = context;
        this.task_id = task_id;
        this.task_text = task_text;
        this.task_status = task_status;
    }



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.task_layout, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.task.setText(String.valueOf(task_text.get(position)));
        //String status=String.valueOf(task_status.get(position));
        int status=Integer.parseInt(String.valueOf(task_status.get(position)));
        if(status==1){
            holder.mCheckBox.setChecked(true);
        }
        else{
            holder.mCheckBox.setChecked(false);
        }


       holder.mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                com.example.noties20.MyDatabaseHelper myDB = new com.example.noties20.MyDatabaseHelper(context);
                if(holder.mCheckBox.isChecked())
                {
                    myDB.updateStatus(String.valueOf(task_id.get(position)),1);
                    Toast.makeText(context, "The task is marked as completed!!", Toast.LENGTH_SHORT).show();
                }
                else if(!holder.mCheckBox.isChecked())
                {
                    myDB.updateStatus(String.valueOf(task_id.get(position)),0);
                    Toast.makeText(context, "The task is marked as uncompleted!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                PopupMenu menu = new PopupMenu(context,view);
                menu.getMenu().add("DELETE");
                menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getTitle().equals("DELETE")){
                            //delete the note
                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
                            builder.setTitle("Delete " + holder.task.getText() + " ?");
                            builder.setMessage("Are you sure you want to delete " + holder.task.getText() + " ?");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    com.example.noties20.MyDatabaseHelper myDB = new com.example.noties20.MyDatabaseHelper(context);
                                    myDB.deleteTask(String.valueOf(task_id.get(position)));
                                    Intent intent = new Intent(context, EditTask.class);
                                    activity.startActivityForResult(intent, 1);


                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                            builder.create().show();
                        }
                        return true;
                    }
                });
                menu.show();

                return true;
            }
        });


        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditTask.class);
                intent.putExtra("id", String.valueOf(task_id.get(position)));
                intent.putExtra("status", String.valueOf(task_status.get(position)));
                intent.putExtra("text", String.valueOf(task_text.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }


    @Override
    public int getItemCount() {
        return task_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        CheckBox mCheckBox;
        TextView task;
        CardView mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCheckBox = itemView.findViewById(R.id.statustask);
            task=itemView.findViewById(R.id.tasktext);
            mainLayout=itemView.findViewById(R.id.tasklayout);
        }
    }
}
