package com.example.criminalintent_v1;
import static com.example.criminalintent_v1.database.crimeDbSchema.CrimeTable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.criminalintent_v1.database.CrimeBasehelper;
import com.example.criminalintent_v1.database.CrimeCursorWrapper;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CrimeLab {

    private static CrimeLab sCrimeLab;


    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static ContentValues getContentValues(Crime c)
    {
        ContentValues values = new ContentValues();
        values.put(CrimeTable.Cols.UUID,c.getId().toString());
        values.put(CrimeTable.Cols.TITLE,c.getTitle());
        values.put(CrimeTable.Cols.DATE,c.getDate().getTime());
        values.put(CrimeTable.Cols.SOLVED,c.isSolved()?1:0);

        return values;
    }

    public static CrimeLab get(Context context)
    {
        if(sCrimeLab==null)
            sCrimeLab = new CrimeLab(context);

        return sCrimeLab;
    }

    public void addCrime(Crime c)
    {
        ContentValues values =  getContentValues(c);
        mDatabase.insert(CrimeTable.NAME,null,values);
    }

    public void updateCrime(Crime c)
    {
        String uuidString = c.getId().toString();
        ContentValues values = getContentValues(c);

        mDatabase.update(CrimeTable.NAME,values,CrimeTable.Cols.UUID + "= ?",new String[]{uuidString});
    }

    private CrimeCursorWrapper querycrimes(String whereClause, String[] whereArgs)
    {
        Cursor cursor = mDatabase.query(CrimeTable.NAME,
                null,whereClause,whereArgs,null,null,null);
        return new CrimeCursorWrapper(cursor);

    }

    private CrimeLab(Context context)
    {
        mContext = context.getApplicationContext();
        mDatabase = new CrimeBasehelper(mContext).getWritableDatabase();


    }

    public List<Crime> getCrimes() {

        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = querycrimes(null,null);

        try{
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }
         return crimes;
    }

    public Crime getCrime(UUID id)
    {
        CrimeCursorWrapper cursor = querycrimes(CrimeTable.Cols.UUID + "=?",new String[]{id.toString()});

        try{
            if(cursor.getCount()==0)
                return null;
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }
}
