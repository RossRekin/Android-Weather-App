package pu.fmi.rainytime.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import pu.fmi.rainytime.models.Search;

public class DbHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "RainyTime.sqlite";
    public static final String ERROR_TAG = "MyErrorTag";


    public static final String TABLE_SEARCH = "search";
    public static final String SEARCH_COLUMN_ID = "id";
    public static final String SEARCH_COLUMN_LOCATION = "location";
    public static final String SEARCH_COLUMN_TIMESTAMP = "timestampt";

    public static final String CREATE_TABLE_SEARCH = "CREATE TABLE " + TABLE_SEARCH + "('" +
            SEARCH_COLUMN_ID + "' INTEGER PRIMARY KEY AUTOINCREMENT," +
            "'" + SEARCH_COLUMN_LOCATION + "' VARCHAR(40) NOT NULL," +
            "'" + SEARCH_COLUMN_TIMESTAMP + "' VARCHAR(40) NOT NULL)";

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SEARCH);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_SEARCH);
        onCreate(db);
    }

    public boolean createSearchRecord(Search search){
        SQLiteDatabase db = null;

        try{
            db = getWritableDatabase();

            ContentValues cv = new ContentValues();

            cv.put(SEARCH_COLUMN_LOCATION, search.getLocation());
            cv.put(SEARCH_COLUMN_TIMESTAMP, search.getTimestamp());
            return db.insert(TABLE_SEARCH,null,cv) != -1;
        }catch (SQLException e){
            Log.wtf(ERROR_TAG,e.getMessage());
        }finally {
            if (db!=null) db.close();
        }
        return false;
    }

    public ArrayList<Search> getSearchRecords(){
        String query= "SELECT * FROM "+ TABLE_SEARCH;
        ArrayList<Search> searchRecords= new ArrayList<>();
        SQLiteDatabase db = null;
        try {
            db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query,null);
            if (cursor.moveToFirst()){
                do {
                    String id = cursor.getString(0);
                    String location = cursor.getString(1);
                    String timestamp = cursor.getString(2);
                    Search currentSearch = new Search(id,location,timestamp);
                    searchRecords.add(currentSearch);

                }while (cursor.moveToNext());
                cursor.close();
            }
        }catch (SQLException e){
            Log.wtf(ERROR_TAG, e.getMessage());
        }
        finally {
            if (db!=null) db.close();
        }

        return searchRecords;
    }

    public void deleteSearch(Search search){
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.delete(TABLE_SEARCH,SEARCH_COLUMN_ID + " = ? ",new String[]{String.valueOf(search.getId())});
        }catch (SQLException e){
            Log.wtf(ERROR_TAG,e.getMessage());
        }finally {
            if (db!=null) db.close();
        }
    }

    public void deleteAllSearchRecords(){
        SQLiteDatabase db = null;
        try {
            db = getWritableDatabase();
            db.delete(TABLE_SEARCH,null,null);
        }catch (SQLException e){
            Log.wtf(ERROR_TAG,e.getMessage());
        }finally {
            if (db!=null) db.close();
        }
    }
}
