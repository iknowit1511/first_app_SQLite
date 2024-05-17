package com.example.sqlite;

import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class UpdateActivity extends AppCompatActivity {
    String id, name, age, location, password;
    EditText name_input, age_input, location_input, password_input;
    Button update_button, delete_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        name_input = findViewById(R.id.name_input2);
        age_input = findViewById(R.id.age_input2);
        location_input = findViewById(R.id.location_input2);
        password_input = findViewById(R.id.password_input2);
        update_button = findViewById(R.id.updatate_button2);
        delete_button = findViewById(R.id.delete_button);
        //Lần đầu gọi
        getAndSetIntentData();

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setTitle(name);
        }

        update_button.setOnClickListener(v -> {
            //Mỗi lần gọi
            MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
            name=name_input.getText().toString().trim();
            age=age_input.getText().toString().trim();
            location=location_input.getText().toString().trim();
            password=password_input.getText().toString().trim();
            myDB.updateData(id, name, age, location, password);
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmdialog();

            }
        });


    }

    public String decryptPassword(String encryptedPassword) {
        try {
            String key = "Thanh123pyzxcvbn"; // 16 char secret key
            String initVector = "RandomInitVector"; // 16 bytes IV

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

            byte[] original = cipher.doFinal(Base64.decode(encryptedPassword, Base64.DEFAULT));

            return new String(original);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }
    void getAndSetIntentData(){
        if (getIntent().hasExtra("id") && getIntent().hasExtra("Ten") &&
                getIntent().hasExtra("Tuoi") && getIntent().hasExtra("Vi tri" )){
            // lấy dữ liệu từ trang
            id = getIntent().getStringExtra("id");
            name = getIntent().getStringExtra("Ten");
            age = getIntent().getStringExtra("Tuoi");
            location = getIntent().getStringExtra("Vi tri");
            password = getIntent().getStringExtra("Mat khau");

            // đưa dữ liệu vào màn hình
            name_input.setText(name);
            age_input.setText(age);
            location_input.setText(location);
            password_input.setText(decryptPassword(password));

        } else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }



    void confirmdialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Xóa " + name + " ?");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + name + " không ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();
    }
}