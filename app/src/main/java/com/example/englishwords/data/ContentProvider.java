package com.example.englishwords.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.UserDictionary;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.CursorLoader;
import com.example.englishwords.data.Contract.Word;

public class ContentProvider extends android.content.ContentProvider {
    DbOpenHelper openHelper;

    public static final int TABLE_WORDS = 111;
    public static final int TABLE_WORD = 123;

    private static final UriMatcher uriMatcher =
            new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(Contract.AUTHORITY,Contract.PATH_WORDS,TABLE_WORDS);
        uriMatcher.addURI(Contract.AUTHORITY,Contract.PATH_WORDS + "/#",TABLE_WORD);
    }
    @Override
    public boolean onCreate() {
        openHelper = new DbOpenHelper(getContext());
        return true;
    }


    @Override
    public Cursor query( Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {
        SQLiteDatabase db = openHelper.getReadableDatabase();
        Cursor cursor;

        int match = uriMatcher.match(uri);

        switch(match){
            case TABLE_WORDS:
                cursor = db.query(Word.TABLE_NAME,projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            case TABLE_WORD:
                selection = Word.COLUMN_ID+"=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(Word.TABLE_NAME,projection, selection,
                        selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Can't query incorrect URI "
                        + uri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }


    @Override
    public String getType( Uri uri) {
        int match = uriMatcher.match(uri);

        switch (match){
            case TABLE_WORDS:
                return Word.CONTENT_MULTIPLE_ITEMS;
            case TABLE_WORD:
                return Word.CONTENT_SINGLE_ITEM;
            default:
            throw new IllegalArgumentException("Unknown URI "
                    + uri);
        }
    }


    @Override
    public Uri insert( Uri uri,  ContentValues values) {

        SQLiteDatabase db = openHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);
        Log.i("adding word","the word was added");

        switch (match) {
            case TABLE_WORDS:
                long id = db.insert(Word.TABLE_NAME,
                        null, values);
                if (id == -1) {
                    Log.e("insertMethod",
                            "Insertion of data in the table failed for "
                                    + uri);
                    return null;
                }
                getContext().getContentResolver().notifyChange(uri,null);
                return ContentUris.withAppendedId(uri, id);

            default:
                throw new IllegalArgumentException("Insertion of data in " +
                        "the table failed for " + uri);

        }

    }

    @Override
    public int delete( Uri uri,  String selection, String[] selectionArgs) {

        SQLiteDatabase db = openHelper.getWritableDatabase();


        int match = uriMatcher.match(uri);

        int rowsDeleted;

        switch (match) {
            case TABLE_WORDS:
                rowsDeleted = db.delete(Word.TABLE_NAME,selection,selectionArgs);
                break;



            case TABLE_WORD:
                selection = Word._ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(Word.TABLE_NAME,selection,selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Can't delete this URI "
                        + uri);

        }
        if (rowsDeleted!=0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return  rowsDeleted;
    }


    @Override
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {

        SQLiteDatabase db = openHelper.getWritableDatabase();

        int match = uriMatcher.match(uri);

        int rowsUpdated;

        switch (match) {
            case TABLE_WORDS:
                rowsUpdated =  db.update(Word.TABLE_NAME,values,selection,selectionArgs);
                break;

            case TABLE_WORD:
                selection = Word._ID + "=?";
                selectionArgs = new String[]
                        {String.valueOf(ContentUris.parseId(uri))};
                rowsUpdated =  db.update(Word.TABLE_NAME,values,selection,selectionArgs);
                break;


            default:
                throw new IllegalArgumentException("Can't update this URI "
                        + uri);

        }
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
