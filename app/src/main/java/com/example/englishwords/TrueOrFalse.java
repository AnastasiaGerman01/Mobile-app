package com.example.englishwords;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.englishwords.data.Contract;

import com.example.englishwords.data.Contract.Word;

public class TrueOrFalse extends AppCompatActivity {

    int word1;
    int word2;
    static private int count_points = 0;
    Button button1;
    Button button2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_or_false);
        button1 = findViewById(R.id.button_false1);
        button2 = findViewById(R.id.button_false2);
        play();
        setTitle("True or False");
    }
    void play(){
        String [] projection = {Contract.Word.COLUMN_ID, Word.COLUMN_DEFINITION, Contract.Word.COLUMN_WORD};
        Cursor cursor = getContentResolver().query(Contract.Word.CONTENT_URI,projection, null,null,null);
        int count = cursor.getCount();
        if (count_points==5){
            count_points = 0;
            word1 = (int)(Math.random()*count+1);
            word2 = word1;
        }
        else{
            word1 = (int)(Math.random()*count+1);
            word2 = (int)(Math.random()*count+1);
            count_points+=1;
        }

        Cursor cursor1 = getContentResolver().query(Contract.Word.CONTENT_URI,projection, Contract.Word.COLUMN_ID + "=?",new String[]{String.valueOf(word1)},null);
        Cursor cursor2 = getContentResolver().query(Contract.Word.CONTENT_URI,projection, Contract.Word.COLUMN_ID + "=?",new String[]{String.valueOf(word2)},null);
        cursor1.moveToFirst();
        cursor2.moveToFirst();

        int Index1 = cursor1.getColumnIndex(Word.COLUMN_WORD);
        int Index2 = cursor2.getColumnIndex(Word.COLUMN_DEFINITION);

        button1.setText(cursor1.getString(Index1));
        button2.setText(cursor2.getString(Index2));
    }

    public void it_is_true(View view) {
        if (word1==word2){
            Toast.makeText(this,"You are right",Toast.LENGTH_LONG).show();
            play();
        }
        else{
            Toast.makeText(this,"Try one more time",Toast.LENGTH_LONG).show();
        }
    }

    public void it_is_false(View view) {
        if (word1!=word2){
            Toast.makeText(this,"You are right",Toast.LENGTH_LONG).show();
            play();
        }
        else{
            Toast.makeText(this,"Try one more time",Toast.LENGTH_LONG).show();
        }
    }
}