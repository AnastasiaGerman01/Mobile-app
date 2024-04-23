package com.example.englishwords;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.englishwords.data.Contract;

import com.example.englishwords.data.Contract.Word;

public class ChooseTheCorrectAnswer extends AppCompatActivity {

    Button button;
    Button button1;
    Button button2;
    Button button3;
    Button button4;
    int word;
    int word1;
    int word2;
    int word3;
    int word4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_the_correct_answer);
        button = findViewById(R.id.button_true);
        button1 = findViewById(R.id.button_true_1);
        button2 = findViewById(R.id.button_true_2);
        button3 = findViewById(R.id.button_true_3);
        button4 = findViewById(R.id.button_true_4);
        play();
        setTitle("Choose the correct answer");
    }

    void play() {
        String[] projection = {Word.COLUMN_ID, Word.COLUMN_WORD, Word.COLUMN_TRANSLATION, Word.COLUMN_DEFINITION};
        Cursor cursor = getContentResolver().query(Contract.Word.CONTENT_URI, projection, null, null, null);
        int count = cursor.getCount();
        word1 = (int) (Math.random() * (count ) + 1);
        word2 = (int) (Math.random() * (count) + 1);
        while (word1 == word2) {
            word2 = (int) (Math.random() * (count ) + 1);
        }
        word3 = (int) (Math.random() * (count ) + 1);
        while ((word1 == word3) || (word3 == word2)) {
            word3 = (int) (Math.random() * (count ) + 1);
        }
        word4 = (int) (Math.random() * (count ) + 1);
        while ((word1 == word4) || (word4 == word2) || (word3 == word4)) {
            word4 = (int) (Math.random() * (count ) + 1);
        }

        int rand = (int) (Math.random() * 4 + 1);

        Log.i("rand", ""+rand);

        if (rand == 1) {
            word = word1;
        } else if (rand == 2) {
            word = word2;
        } else if (rand == 3) {
            word = word3;
        } else if (rand == 4) {
            word = word4;
        }
        Log.i("cursor", ""+word+" "+ word1+" "+ word2+" "+ word3+" "+ word4);
        Cursor cursor1 = getContentResolver().query(Contract.Word.CONTENT_URI, projection, Word.COLUMN_ID + "=?",new String[]{String.valueOf(word)},null);
        Cursor cursor2 = getContentResolver().query(Contract.Word.CONTENT_URI, projection, Word.COLUMN_ID + "=?",new String[]{String.valueOf(word1)},null);
        Cursor cursor3 = getContentResolver().query(Contract.Word.CONTENT_URI, projection, Word.COLUMN_ID + "=?",new String[]{String.valueOf(word2)},null);
        Cursor cursor4 = getContentResolver().query(Contract.Word.CONTENT_URI, projection, Word.COLUMN_ID + "=?",new String[]{String.valueOf(word3)},null);
        Cursor cursor5 = getContentResolver().query(Contract.Word.CONTENT_URI, projection, Word.COLUMN_ID + "=?",new String[]{String.valueOf(word4)},null);

        if(cursor1!=null){
            cursor1.moveToFirst();
        }
        if(cursor2!=null){
            cursor2.moveToFirst();
        }
        if(cursor3!=null){
            cursor3.moveToFirst();
        }
        if(cursor4!=null){
            cursor4.moveToFirst();
        }
        if(cursor5!=null){
            cursor5.moveToFirst();
        }

        int Index1 = cursor1.getColumnIndex(Word.COLUMN_WORD);
        int Index2 = cursor2.getColumnIndex(Word.COLUMN_TRANSLATION);
        int Index3 = cursor3.getColumnIndex(Word.COLUMN_TRANSLATION);
        int Index4 = cursor4.getColumnIndex(Word.COLUMN_TRANSLATION);
        int Index5 = cursor5.getColumnIndex(Word.COLUMN_TRANSLATION);

        button.setText(" "+ cursor1.getString(Index1)+" ");
        button1.setText(" "+cursor2.getString(Index2)+" ");
        button2.setText(" "+cursor3.getString(Index3)+" ");
        button3.setText(" "+cursor4.getString(Index4)+" ");
        button4.setText(" "+cursor5.getString(Index5)+" ");

        /*int rand1 = (int) (Math.random()*4+1);
        int rand2 = (int) (Math.random()*4+1);
        while (rand1==rand2){
            rand2 = (int) (Math.random()*4+1);
        }
        int rand3 = (int) (Math.random()*4+1);
        while ((rand1==rand3)||(rand2==rand3)){
            rand3 = (int) (Math.random()*4+1);
        }
        int rand4  = (int) (Math.random()*4+1);
        while ((rand1==rand4)||(rand2==rand4) ||(rand3==rand4)){
            rand4  = (int) (Math.random()*4+1);
        } */


    }

    public void button_1(View view) {
        if (word1==word){
            Toast.makeText(this, "You are right",Toast.LENGTH_LONG).show();
            play();
        }
        else{
            Toast.makeText(this,"Try one more time", Toast.LENGTH_LONG).show();
        }

    }


    public void button_2(View view) {
        if (word2==word){
            Toast.makeText(this, "You are right",Toast.LENGTH_LONG).show();
            play();
        }
        else{
            Toast.makeText(this,"Try one more time", Toast.LENGTH_LONG).show();
        }
    }

    public void button_3(View view) {
        if (word3==word){
            Toast.makeText(this, "You are right",Toast.LENGTH_LONG).show();
            play();
        }
        else{
            Toast.makeText(this,"Try one more time", Toast.LENGTH_LONG).show();
        }
    }

    public void button_4(View view) {
        if (word4==word){
            Toast.makeText(this, "You are right",Toast.LENGTH_LONG).show();
            play();
        }
        else{
            Toast.makeText(this,"Try one more time", Toast.LENGTH_LONG).show();
        }
    }
}