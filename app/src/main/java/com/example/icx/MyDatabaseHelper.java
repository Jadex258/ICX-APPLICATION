package com.example.icx;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class MyDatabaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "db.sqlite3";
    private static final int DATABASE_VERSION = 2;

    // Table name
    private static final String TABLE_NAME = "auth_user";

    // Table columns
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_LAST_LOGIN = "last_login";
    private static final String COLUMN_IS_SUPERUSER = "is_superuser";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_LAST_NAME = "last_name";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_IS_STAFF = "is_staff";
    private static final String COLUMN_IS_ACTIVE = "is_active";
    private static final String COLUMN_DATE_JOINED = "date_joined";
    private static final String COLUMN_FIRST_NAME = "first_name";

    // Create table SQL query
    private static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_USERNAME + " TEXT,"
                    + COLUMN_PASSWORD + " VARCHAR(50) NOT NULL,"
                    + COLUMN_LAST_LOGIN + " DATETIME NOT NULL,"
                    + COLUMN_IS_SUPERUSER + " BOOL NOT NULL,"
                    + COLUMN_LAST_NAME + " VARCHAR(150) NOT NULL,"
                    + COLUMN_EMAIL + " VARCHAR(254) NOT NULL,"
                    + COLUMN_IS_STAFF + " BOOL NOT NULL,"
                    + COLUMN_IS_ACTIVE + " BOOL NOT NULL,"
                    + COLUMN_DATE_JOINED + " DATETIME NOT NULL,"
                    + COLUMN_FIRST_NAME + " VARCHAR(150) NOT NULL"
                    + ")";


    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL(CREATE_TABLE);
            Log.d("MyDatabaseHelper", "Table created successfully");
        } catch (SQLException e) {
            Log.e("MyDatabaseHelper", "Table creation failed: " + e.getMessage());
            Toast.makeText(context, "Table creation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // drop table if exists and create new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        Toast.makeText(context, "Table dropped and recreated successfully", Toast.LENGTH_SHORT).show();
    }

    public String getUserFirstName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_FIRST_NAME};
        String selection = COLUMN_USERNAME + " = ?";
        String[] selectionArgs = {username};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int firstNameIndex = cursor.getColumnIndexOrThrow(COLUMN_FIRST_NAME);
            String firstName = cursor.getString(firstNameIndex);
            cursor.close();
            return firstName;
        }
        return null;
    }
}
