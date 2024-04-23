package com.example.englishwords;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.englishwords.data.Contract;
import com.example.englishwords.data.Contract.Word;

public class QuizActivity extends AppCompatActivity {
    Button button_1;
    EditText editText1;
    String answer;
    String check;
    Cursor cursor1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        play();
        setTitle("Quiz");
    }

    public void check(View view){
        editText1 = findViewById(R.id.quiz_edit_text);
        answer = editText1.getText().toString().trim();
        answer.toLowerCase();
        int WordIndex = cursor1.getColumnIndex(Word.COLUMN_WORD);
        check = cursor1.getString(WordIndex);
        check.toLowerCase();
        if (answer.equals(check)){
            Toast.makeText(this,"You are right", Toast.LENGTH_LONG).show();
            play();
        }
        else{
            Toast.makeText(this,"Try one more time", Toast.LENGTH_LONG).show();
        }
    }
    public void play(){
        String [] projection = {Word.COLUMN_ID, Word.COLUMN_QUESTION, Word.COLUMN_WORD};
        Cursor cursor = getContentResolver().query(Word.CONTENT_URI,projection, null,null,null);
        int count = cursor.getCount();
        int rand = (int) (Math.random()*(count)+1);
        Log.i("count", ""+ count+" "+ rand);
        cursor1 = getContentResolver().query(Word.CONTENT_URI,projection, Word.COLUMN_ID + "=?",new String[]{String.valueOf(rand)},null);
        if (cursor1 != null){
            cursor1.moveToFirst();
        }
        int QuestionIndex = cursor1.getColumnIndex(Word.COLUMN_QUESTION);
        button_1 = findViewById(R.id.button_quiz_1);
        button_1.setText(" " + cursor1.getString(QuestionIndex)+" ");
    }
}