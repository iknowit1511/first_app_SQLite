package com.example.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class DisplayQueryActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    MyDatabaseHelper myDB;
    ArrayList<String> human_id, human_name, human_age, human_location, human_password;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_displayquery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView2);
        myDB = new MyDatabaseHelper(DisplayQueryActivity.this);
        human_id = new ArrayList<>();
        human_name = new ArrayList<>();
        human_age = new ArrayList<>();
        human_location = new ArrayList<>();
        human_password = new ArrayList<>();

        displayQuery();
        customAdapter = new CustomAdapter(DisplayQueryActivity.this,this, human_id, human_name, human_age, human_location, human_password);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1)
            recreate();
    }
    void displayQuery()
    {
        String column_input = getIntent().getStringExtra("column");
        String comp_input = getIntent().getStringExtra("comp");
        String value_input = getIntent().getStringExtra("value");

        Cursor cursor = myDB.QueryData(column_input, comp_input, value_input);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Truy vấn hông có kết quả trả về", Toast.LENGTH_SHORT).show();
        }
        else {
            while (cursor.moveToNext()) {
                human_id.add(cursor.getString(0));
                human_name.add(cursor.getString(1));
                human_age.add(cursor.getString(2));
                human_location.add(cursor.getString(3));
            }
        }
    }
}