package com.example.sqlite;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class QueryActivity extends AppCompatActivity {
    AutoCompleteTextView column_input, comp_input;
    EditText value_input;
    Button query_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_query);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        column_input = findViewById(R.id.column_input);
        comp_input = findViewById(R.id.comp_input);
        value_input = findViewById(R.id.value_input);
        String[] sug_column = getResources().getStringArray(R.array.sug_column); // Thay thế bằng danh sách từ gợi ý của bạn
        ArrayAdapter<String> column_adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sug_column);
        column_input.setAdapter(column_adapter);
        column_input.setThreshold(0); // Cho phép gợi ý hiển thị ngay cả khi không có ký tự nào
        column_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    column_input.showDropDown(); // Hiển thị gợi ý khi AutoCompleteTextView nhận focus
                }
            }
        });
        String[] sug_comp = getResources().getStringArray(R.array.sug_comp);
        ArrayAdapter<String> comp_adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, sug_comp);
        comp_input.setAdapter(comp_adapter);
        comp_input.setThreshold(0); // Cho phép gợi ý hiển thị ngay cả khi không có ký tự nào
        comp_input.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    comp_input.showDropDown(); // Hiển thị gợi ý khi AutoCompleteTextView nhận focus
                }
            }
        });



        query_button = findViewById(R.id.query_button);
        query_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(QueryActivity.this, DisplayQueryActivity.class);
                intent.putExtra("column", column_input.getText().toString().trim());
                intent.putExtra("comp", comp_input.getText().toString().trim());
                intent.putExtra("value", value_input.getText().toString().trim());
                startActivity(intent);
            }
        });
    }
}