package com.example.sqlite;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Base64;
import android.widget.Toast;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.security.SecureRandom;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "HR.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "My_database";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "Ten";
    private static final String COLUMN_AGE = "Tuoi";
    private static final String COLUMN_LOCATION = "Vi_tri";
    private static final String COLUMN_PASSWORD = "Mau_khau";


    private static final int ITERATIONS = 10000; // Adjust based on security needs and performance
    private static final int KEY_LENGTH = 256; // Adjust based on security needs

    MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT NOT NULL, " +
                COLUMN_AGE + " INTEGER, " +
                COLUMN_LOCATION + " TEXT, " +
                COLUMN_PASSWORD + " TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }



    // Encryption function
    public String encryptPassword(String password) {
        try {
            String key = "Thanh123pyzxcvbn"; // 16 char secret key
            String initVector = "RandomInitVector"; // 16 bytes IV

            IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

            byte[] encrypted = cipher.doFinal(password.getBytes());
            return Base64.encodeToString(encrypted, Base64.DEFAULT);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }


    // Decryption function


    void addHuman(String name, int age, String location, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_LOCATION, location);
        String hashedPassword = encryptPassword(password);
        cv.put(COLUMN_PASSWORD, hashedPassword);

        long result = db.insert(TABLE_NAME, null, cv);
        if (result == -1) {
            Toast.makeText(context, "Thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Thêm thành công", Toast.LENGTH_SHORT).show();
        }
    }

    Cursor readAllData () {
        String que = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(que, null);
        }
        return cursor;
    }
    void updateData(String row_id, String name, String age, String location, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_AGE, age);
        cv.put(COLUMN_LOCATION, location);
        String hashedPassword = encryptPassword(password);
        cv.put(COLUMN_PASSWORD, hashedPassword);
        long result = db.update(TABLE_NAME, cv, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if (result == -1) {
            Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME );
    }

    public static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch(NumberFormatException e) {
            return false;
        }
    }
    Cursor QueryData(String column, String comp, String value){
        String que;
        if(isInteger(value))
            que = "SELECT * FROM " + TABLE_NAME + " WHERE " + column + comp + value;
        else
            que = "SELECT * FROM " + TABLE_NAME + " WHERE " + column + comp + "\"" +value + "\"";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        if (db != null) {
            cursor = db.rawQuery(que, null);
        }
        return cursor;
    }

}
