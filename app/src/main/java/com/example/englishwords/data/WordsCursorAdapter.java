package com.example.englishwords.data;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.englishwords.data.Contract.Word;

import com.example.englishwords.R;

public class WordsCursorAdapter extends android.widget.CursorAdapter {
    public WordsCursorAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return LayoutInflater.from(context).inflate(R.layout.item,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView word_view = view.findViewById(R.id.word_view);
        TextView translation_view = view.findViewById(R.id.translation_view);
        String word = cursor.getString(cursor.getColumnIndexOrThrow(Word.COLUMN_WORD));
        String translation = cursor.getString(cursor.getColumnIndexOrThrow(Word.COLUMN_TRANSLATION));
        word_view.setText(word);
        translation_view.setText(translation);

    }
}
