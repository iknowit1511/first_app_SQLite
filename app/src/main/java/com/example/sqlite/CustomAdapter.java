package com.example.sqlite;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;
    private Activity activity;
    private final ArrayList<String> human_id, human_name, human_age, human_location, human_password;
    int position;
   


    CustomAdapter (Activity activity, Context content,
                   ArrayList<String> human_id,
                   ArrayList<String> human_name,
                   ArrayList<String> human_age,
                   ArrayList<String> human_location,
                   ArrayList<String> human_password){
        this.activity = activity;
        this.context = content;
        this.human_id = human_id;
        this.human_name = human_name;
        this.human_age = human_age;
        this.human_location = human_location;
        this.human_password = human_password;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        this.position = position;
        holder.human_id_txt.setText(String.valueOf(human_id.get(position)));
        holder.human_name_txt.setText(String.valueOf(human_name.get(position)));
        holder.human_age_txt.setText(String.valueOf(human_age.get(position)));
        holder.human_location_txt.setText(String.valueOf(human_location.get(position)));

        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(human_id.get(position)));
                intent.putExtra("Ten", String.valueOf(human_name.get(position)));
                intent.putExtra("Tuoi", String.valueOf(human_age.get(position)));
                intent.putExtra("Vi tri", String.valueOf(human_location.get(position)));
                intent.putExtra("Mat khau", String.valueOf(human_password.get(position)));
                activity.startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return human_id.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView human_id_txt, human_name_txt, human_age_txt, human_location_txt;
        ConstraintLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            human_id_txt = itemView.findViewById(R.id.human_id_txt);
            human_name_txt = itemView.findViewById(R.id.human_name_txt);
            human_age_txt = itemView.findViewById(R.id.human_age_txt);
            human_location_txt = itemView.findViewById(R.id.human_location_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);

        }

    }

}
