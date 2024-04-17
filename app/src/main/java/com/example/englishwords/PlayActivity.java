package com.example.englishwords;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PlayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        setTitle("Choose the game");
    }

    public void quiz(View view) {
        Intent intent = new Intent(PlayActivity.this, QuizActivity.class);
        startActivity(intent);
    }

    public void choose_play(View view) {
        Intent intent = new Intent(PlayActivity.this, ChooseTheCorrectAnswer.class);
        startActivity(intent);
    }

    public void true_or_false_play(View view) {
        Intent intent = new Intent(PlayActivity.this, TrueOrFalse.class);
        startActivity(intent);
    }
}