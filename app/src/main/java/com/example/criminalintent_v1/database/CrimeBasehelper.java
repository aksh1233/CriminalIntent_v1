package com.example.criminalintent_v1.database;

import static com.example.criminalintent_v1.database.crimeDbSchema.CrimeTable;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class CrimeBasehelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";
    public CrimeBasehelper(Context context)
    {
        super(context,DATABASE_NAME,null,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create table " + CrimeTable.NAME + "(" +
                "_id integer primary key autoincrement" + "," +
                CrimeTable.Cols.UUID + "," +
                CrimeTable.Cols.TITLE + "," +
                CrimeTable.Cols.DATE + "," +
                CrimeTable.Cols.SOLVED + ","+
                CrimeTable.Cols.SUSPECT +")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion)
    {

    }
}
