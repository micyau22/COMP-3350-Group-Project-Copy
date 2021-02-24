package com.example.comp3350;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper
{
    private static final String TAG = "DatabaseHelper";

    private static final String FILE_NAME = "profiles.db";
    private static final String TABLE_NAME = "profiles";
    private static final String COL1 = "ID";
    private static final String COL2 = "email";
    private static final String COL3 = "name";
    private static final String COL4 = "gender";
    private static final String COL5 = "weight";
    private static final String COL6 = "age";
    private static final String COL7 = "BMI";


    public DatabaseHelper (Context context)
    {
        super(context, FILE_NAME, null, 1);
    }

    //this will only process when we create a new database
    @Override
    public void onCreate(SQLiteDatabase db)
    {
       String createTable = "CREATE TABLE " + TABLE_NAME + " (" + COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COL2 + " TEXT, " +
               COL3 + " TEXT, " + COL4 + " TEXT, " + COL5 + " INTEGER, " +
               COL6 + " INTEGER, " + COL7 + " INTEGER) ";
        db.execSQL(createTable);
    }

    //this runs only when we update the version of the application
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(User newUser)
    {
        //assume we didn't add data(item) to the table
        boolean result = false;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        //insert user data in database file
        contentValues.put(COL2, newUser.getEmail());
        contentValues.put(COL3, newUser.getName());
        contentValues.put(COL4, newUser.getGender());
        contentValues.put(COL5, newUser.getWeight());
        contentValues.put(COL6, newUser.getAge());
        contentValues.put(COL7, newUser.getBmi());

        Log.d(TAG, "addData: Adding a new user to " + TABLE_NAME);

        // -1 if not inserted, otherwise it was inserted
        long check = db.insert(TABLE_NAME, null, contentValues);

        if (check  != -1)
        {
            result = true;
        }
        return result;
    }

    public User getSomeone(String input)
    {
        User result;

        //Query the database to pull a user
        String queryString = "SELECT * FROM " + TABLE_NAME + " WHERE NAME = " + input;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor;
        cursor = db.rawQuery(queryString, null);

        //if we found our user
        if (cursor.moveToFirst())
        {
            int userID = cursor.getInt(0);
            String userEmail = cursor.getString(1);
            String userName = cursor.getString(2);
            String userGender = cursor.getString(3);
            int userWeight = cursor.getInt(4);
            int userAge = cursor.getInt(5);
            int userBMI = cursor.getInt(6);

            result = new User(userID, userName, userEmail, userAge, userWeight,
                    userGender, userBMI);
        }
        //empty User
        else
        {
            result = new User();
        }
        //close db and cursor when done
        db.close();
        cursor.close();
        return result;
    }
}
