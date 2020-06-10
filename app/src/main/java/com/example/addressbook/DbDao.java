package com.example.addressbook;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbDao extends SQLiteOpenHelper {

    private static final String P_ID = "ID";
    private static final String Table_Name = "Person_CRUD";
    private static final String PERSON_NAME = "Person_Name";
    private static final String PERSON_EMAIL = "Person_Email";
    private static final String PERSON_EMAIL1 = "Person_Email1";
    private static final String PERSON_ADDRESS = "Person_Address";
    private static final String PERSON_ADDRESS1 = "Person_Address1";
    private static final String PERSON_MOBILE_NO = "Person_Mobile_No";
    private static final String PERSON_MOBILE_NO1 = "Person_Mobile_No1";
    private static final String PERSON_IMAGE = "Person_Image";


    DbDao(@Nullable Context context) {
        super(context, "person.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createPersonTable = "CREATE TABLE " + Table_Name + "(" + P_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
                PERSON_NAME + "TEXT," + PERSON_EMAIL + "TEXT," + PERSON_EMAIL1 + "TEXT," + PERSON_MOBILE_NO +
                "LONG," + PERSON_MOBILE_NO1 + " LONG," + PERSON_ADDRESS + " Text," + PERSON_ADDRESS1 + " Text," + PERSON_IMAGE
                + "BLOB" + ")";
        sqLiteDatabase.execSQL(createPersonTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    void AddPerson(PersonDao obj) {
        //Get Reference to database
        SQLiteDatabase db = getWritableDatabase();
        //Create Content values object and add person to it
        ContentValues values = new ContentValues();
        //Use Key values to insert into database
        values.put(PERSON_NAME, obj.getP_Name());
        values.put(PERSON_ADDRESS, obj.getAddress());
        values.put(PERSON_ADDRESS1, obj.getAddress1());
        values.put(PERSON_MOBILE_NO, obj.getPh_No());
        values.put(PERSON_MOBILE_NO1, obj.getPh_No1());
        values.put(PERSON_EMAIL, obj.getEmail());
        values.put(PERSON_EMAIL1, obj.getEmail1());
        values.put(PERSON_IMAGE,obj.getImage());
        //Insert values to table
        db.insert(Table_Name, null, values);
        //Close Connection
        db.close();
    }
}
