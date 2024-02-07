package com.mohware.ikujatwende;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {
    List<EventsModel> eventsList = new ArrayList<>();

    public DBHelper(Context context) {
        super(context, "EventsDb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Events(name TEXT primary key, location TEXT, reporter TEXT, time TEXT," +
                "date TEXT, path TEXT, descpt TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {

        DB.execSQL("drop Table if exists Events");
    }

    public Boolean insertData(String name, String location, String reporter, String Time, String date, String Path, String Desc) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues Cv = new ContentValues();
        Cv.put("name", name);
        Cv.put("location", location);
        Cv.put("reporter", reporter);
        Cv.put("Time", Time);
        Cv.put("date", date);
        Cv.put("Path", Path);
        Cv.put("descpt", Desc);

        long result = DB.insert("Events", null, Cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean updateData(String namex, String location, String reporter, String Time, String date, String Path) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues Cv = new ContentValues();
        Cv.put("location", location);
        Cv.put("reporter", reporter);
        Cv.put("Time", Time);
        Cv.put("date", date);
        Cv.put("Path", Path);
        Cursor cursor = DB.rawQuery("Select * from Events where name =?", new String[]{namex});
        if (cursor.getCount() > 0) {
            long result = DB.update("Events", Cv, "name=?", new String[]{namex});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Boolean deleteData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Events where name =?", new String[]{name});
        if (cursor.getCount() > 0) {
            long result = DB.delete("Events", "name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor getData() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Events", null);
        return cursor;
    }

    public Cursor srcData(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Events where name like '%" + name + "%'", null);
        return cursor;
    }

    public Boolean deleteall() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Events", null);
        if (cursor.getCount() > 0) {
            long result = DB.delete("Events", null, null);
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public Cursor alterTB() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Alter Table Events Add Column descpt TEXT", null);
        return cursor;
    }


}
