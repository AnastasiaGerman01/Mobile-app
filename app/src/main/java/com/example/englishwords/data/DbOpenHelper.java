package com.example.englishwords.data;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.englishwords.data.Contract.Word;

public class DbOpenHelper extends SQLiteOpenHelper {
    public DbOpenHelper(Context context) {
        super(context, Contract.DATABASE_NAME, null, Contract.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table = "CREATE TABLE " + Word.TABLE_NAME + "(" +
                Word.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Word.COLUMN_WORD + " TEXT," +
                Word.COLUMN_TRANSLATION + " TEXT,"+
                Word.COLUMN_DEFINITION + " TEXT,"+
                Word.COLUMN_EXAMPLE + " TEXT,"+
                Word.COLUMN_QUESTION + " TEXT,"+
                Word.COLUMN_SYNONYMS + " TEXT" + ")";

        db.execSQL(create_table);
        Log.i("creating table", "table was created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.DATABASE_NAME);
        onCreate(db);
    }
}
